package ua.gaponov.posterminal.entity.orders;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.database.DatabaseRequest;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.entity.DatePeriod;
import ua.gaponov.posterminal.entity.DocumentTypes;
import ua.gaponov.posterminal.entity.PayTypes;
import ua.gaponov.posterminal.entity.cards.Card;
import ua.gaponov.posterminal.entity.cards.CardService;
import ua.gaponov.posterminal.entity.organization.Organization;
import ua.gaponov.posterminal.utils.FilesUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.*;

import static ua.gaponov.posterminal.utils.Constants.ORDER_NUMBER_NAME;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);
    private static final SqlHelper<Order> ORDER_SQL_HELPER = new SqlHelper<>();
    private static final SqlHelper<DeletedOrderItem> DELETED_ORDER_ITEM_SQL_HELPER = new SqlHelper<>();
    private static final String TEMP_FILE_ORDER_BACKUP = "tmp/temp-order.dat";

    public static Order getByGuid(String guid) {
        StatementParameters<String> parameters = StatementParameters.build(guid);
        return ORDER_SQL_HELPER.getOne("select * from orders where order_guid = ?",
                parameters,
                new OrderDatabaseMapper());
    }

    public static Order getByNumber(String number) {
        StatementParameters<String> parameters = StatementParameters.build(number);
        return ORDER_SQL_HELPER.getOne("select * from orders where order_number = ?",
                parameters,
                new OrderDatabaseMapper());
    }

    public static Order getByNumber(String number, boolean itsReturn) {
        if (!itsReturn) {
            return getByNumber(number);
        }
        StatementParameters<String> parameters = StatementParameters.build(number);
        return ORDER_SQL_HELPER.getOne("select * from orders where order_number = ? and doc_type ='ORDER'",
                parameters,
                new OrderDatabaseMapper());
    }

    public static long getCount() {
        return ORDER_SQL_HELPER.getCount("select count(order_guid) from orders");
    }

    public static long getNewOrderNumber() {
        return NumbersService.loadNumber(ORDER_NUMBER_NAME);
    }

    public static List<Order> getAll() {
        return ORDER_SQL_HELPER.getAll("SELECT * FROM orders", new OrderDatabaseMapper());
    }

    public static List<Order> getAllNoUpload() {
        StatementParameters<Boolean> parameters = StatementParameters.build(false);
        return ORDER_SQL_HELPER.getAll("SELECT * FROM orders where upload = ?",
                parameters,
                new OrderDatabaseMapper());
    }

    public static void deleteAll() {
        String sql = """
                    BEGIN TRANSACTION;
                    DELETE FROM orders_detail WHERE order_guid IN (SELECT order_guid FROM orders);
                    DELETE FROM orders;
                    COMMIT;
                """;
        SqlHelper.execSql(sql);
    }

    public static void deleteUploaded() {
        String sql = """
                    BEGIN TRANSACTION;
                    DELETE FROM orders_detail WHERE order_guid IN (SELECT order_guid FROM orders WHERE upload = true);
                    DELETE FROM orders WHERE upload = true;
                    COMMIT;
                """;
        SqlHelper.execSql(sql);
    }

    public static void save(Order order) throws SQLException {
        insert(order);
    }

    private static void insert(Order order) throws SQLException {
        List<DatabaseRequest> requestList = new ArrayList<>();
        requestList.add(getInsertRequest(order));
        List<OrderDetail> details = order.getDetails();
        int line = 1;
        for (OrderDetail detail : details) {
            requestList.add(OrderDetailService.getInsertRequest(order, detail, line));
            line = line + 1;
        }

        if (Objects.nonNull(order.getCard())) {
            double deltaSum = order.getDocumentSum() - order.getPaySum();
            if (deltaSum > 0) {
                Card card = order.getCard();
                card.setDebt(card.getDebt() + deltaSum);
                requestList.add(CardService.getUpdateRequest(card));
            }
        }
        ORDER_SQL_HELPER.execSql(requestList);
    }

    public static DatabaseRequest getInsertRequest(Order order) {
        order.setOrderNumber(getNewOrderNumber());
        String sql = """
                    insert into orders
                    (order_guid, order_number, summa_doc, summa_to_pay, summa_discount, summa_round, summa_pay,
                     doc_type, pay_type, upload, fiscal, internet, fiscal_print, card_guid, prn, auth_code, merch_id, user_guid)
                    values
                    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        StatementParameters<Object> parameters = StatementParameters.build(
                order.getGuid(),
                order.getOrderNumber(),
                order.getDocumentSum(),
                order.getToPaySum(),
                order.getDiscountSum(),
                order.getRoundSum(),
                order.getPaySum(),
                order.getDocumentType().toString(),
                order.getPayType().toString(),
                false,
                order.isFiscal(),
                order.isInternet(),
                order.isFiscalPrint()
        );

        if (Objects.nonNull(order.getCard())) {
            parameters.addAll(order.getCard().getGuid());
        } else {
            parameters.addNull();
        }

        parameters.addAll(order.getPrnCode(), order.getAuthCode(), order.getMerchId());

        if (Objects.nonNull(order.getUser())) {
            parameters.addAll(order.getUser().getGuid());
        } else {
            parameters.addNull();
        }

        return new DatabaseRequest(sql, parameters);
    }

    public static void saveOrderToBackupDir(Map<Integer, Order> orders) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEMP_FILE_ORDER_BACKUP))) {
            oos.writeObject(orders);
        } catch (Exception ex) {
            LOG.error("Error save orders to backup dir", ex);
        }
    }

    public static Map<Integer, Order> loadOrderFromBackupDir() {
        Map<Integer, Order> orders = new HashMap<>();

        if (!FilesUtils.fileExist(TEMP_FILE_ORDER_BACKUP)) {
            return orders;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEMP_FILE_ORDER_BACKUP))) {
            orders = (Map<Integer, Order>) ois.readObject();
            if (orders == null){
                orders = new HashMap<>();
            }
            for (Map.Entry<Integer, Order> entry : orders.entrySet()) {
                Order order = entry.getValue();
                order.setGuid(UUID.randomUUID().toString());
            }
            return orders;
        } catch (Exception ex) {
            LOG.error("Error load orders from backup dir", ex);
        }

        return orders;
    }

    public static Order createOrderByOrganization(Order order, Organization organization) {
        Order tempOrder = new Order();
        tempOrder.setCard(order.getCard());
        tempOrder.setPayType(order.getPayType());
        tempOrder.setDocumentType(order.getDocumentType());
        List<OrderDetail> details = order.getDetails();
        for (OrderDetail detail : details) {
            if (Objects.equals(detail.getProduct().getOrganization(), organization)) {
                tempOrder.getDetails().add(detail);
            }
        }
        tempOrder.recalculateDocumentSum();
        return tempOrder;
    }

    public static Order copyOrder(Order order) {
        Order newOrder;
        try {
            newOrder = (Order) order.clone();
        } catch (CloneNotSupportedException e) {
            newOrder = new Order();
        }
        newOrder.setGuid(UUID.randomUUID().toString());
        for (OrderDetail detail : newOrder.getDetails()) {
            detail.setGuid(UUID.randomUUID().toString());
            detail.setFiscalPrint(false);
            detail.setOrganization(null);
        }
        newOrder.setDocumentType(DocumentTypes.RETURN);
        newOrder.setPayType(PayTypes.CASH);
        newOrder.setFiscalPrint(false);
        newOrder.setAuthCode(null);
        newOrder.setUpload(false);
        newOrder.setPrnCode(null);
        newOrder.setPaySum(0);

        return newOrder;
    }

    public static void confirmUploadDocument(String guid) throws SQLException {
        String sql = """
                    UPDATE orders set upload = true WHERE order_guid = ?;
                """;
        StatementParameters<Object> parameters = StatementParameters.build(guid);
        ORDER_SQL_HELPER.execSql(sql, parameters);
    }

    public static void saveDeletedProduct(OrderDetail orderDetail) {
        String sql = """
                    insert into deleted_products
                    (user_name, product_name, qty, summa)
                    values
                    (?, ?, ?, ?);
                """;
        StatementParameters<Object> parameters = StatementParameters.build(
                AppProperties.getCurrentUser().getName(),
                orderDetail.getProduct().getName(),
                orderDetail.getQty(),
                orderDetail.getSumma()
        );

        try {
            ORDER_SQL_HELPER.execSql(sql, parameters);
            LOG.info("Deleted product " + orderDetail.getProduct().getName() + " " + orderDetail.getQty());
        } catch (Exception ex) {
            LOG.error("Failed save deleted products");
        }
    }

    public static List<DeletedOrderItem> getDeletedProducts(DatePeriod datePeriod) {
        StatementParameters<Object> parameters = StatementParameters.build(
                datePeriod.getStartDate(), datePeriod.getEndDate()
        );
        return DELETED_ORDER_ITEM_SQL_HELPER.getAll("SELECT * FROM DELETED_PRODUCTS where DELETE_DATE_TIME >= ? and DELETE_DATE_TIME <= ?",
                parameters,
                new DeletedOrderItemDatabaseMapper());
    }
}
