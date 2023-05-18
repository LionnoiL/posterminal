package ua.gaponov.posterminal.entity.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.database.DatabaseRequest;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.entity.DocumentTypes;
import ua.gaponov.posterminal.entity.PayTypes;
import ua.gaponov.posterminal.entity.cards.Card;
import ua.gaponov.posterminal.entity.cards.CardService;
import ua.gaponov.posterminal.entity.organization.Organization;
import ua.gaponov.posterminal.entity.shift.ShiftResultService;
import ua.gaponov.posterminal.utils.FilesUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Andriy Gaponov
 */
public class OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);
    private static final SqlHelper<Order> helper = new SqlHelper<>();
    private static final String TEMP_FILE_ORDER_BACKUP = "tmp/temp-order.dat";

    private OrderService() {
    }

    public static Order getByGuid(String guid) {
        StatementParameters<String> parameters = StatementParameters.build(guid);
        return helper.getOne("select * from orders where order_guid = ?",
                parameters,
                new OrderDatabaseMapper());
    }

    public static Order getByNumber(String number) {
        StatementParameters<String> parameters = StatementParameters.build(number);
        return helper.getOne("select * from orders where order_number = ?",
                parameters,
                new OrderDatabaseMapper());
    }

    public static Order getByNumber(String number, boolean itsReturn) {
        if (!itsReturn) {
            return getByNumber(number);
        }
        StatementParameters<String> parameters = StatementParameters.build(number);
        return helper.getOne("select * from orders where order_number = ? and doc_type ='ORDER'",
                parameters,
                new OrderDatabaseMapper());
    }

    public static long getCount() {
        return helper.getCount("select count(order_guid) from orders");
    }

    public static List<Order> getAll() {
        return helper.getAll("SELECT * FROM orders", new OrderDatabaseMapper());
    }

    public static List<Order> getAllNoUpload() {
        StatementParameters<Boolean> parameters = StatementParameters.build(false);
        return helper.getAll("SELECT * FROM orders where upload = ?",
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
        requestList.add(getInsertShiftResultRequest(order));

        if (Objects.nonNull(order.getCard())){
            double deltaSum = order.getDocumentSum() - order.getPaySum();
            if (deltaSum>0){
                Card card = order.getCard();
                card.setDebt(card.getDebt() + deltaSum);
                requestList.add(CardService.getUpdateRequest(card));
            }
        }
        helper.execSql(requestList);
    }

    public static DatabaseRequest getInsertRequest(Order order) {
        order.setOrderNumber(getCount() + 1);
        String sql = """
                    insert into orders
                    (order_guid, order_number, summa_doc, summa_to_pay, summa_discount, summa_round, summa_pay,
                     doc_type, pay_type, upload, fiscal, internet, fiscal_print, card_guid, prn, auth_code)
                    values
                    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
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

        parameters.addAll(order.getPrnCode(), order.getAuthCode());

        return new DatabaseRequest(sql, parameters);
    }

    public static DatabaseRequest getInsertShiftResultRequest(Order order) {
        String sql = """
                    insert into shift_results (USER_GUID, SUMMA_ORDER_CASH, SUMMA_RETURN_CASH,
                                SUMMA_SAFE, SUMMA_ORDER_CARD, SUMMA_SALE, SUMMA_RETURN)
                    values
                                (?, ?, ?, ?, ?, ?, ?)
                """;
        double summaCash = 0;
        double summaCard = 0;
        double summaOrderCash = 0;
        double summaOrderCard = 0;
        double summaReturnCash = 0;
        double summaSale = 0;
        double summaReturn = 0;
        double summaSafe = 0;

        double toPaySum = order.getPaySum();
        if (toPaySum>order.getToPaySum()){
            toPaySum=order.getToPaySum();
        }
        if (Objects.equals(PayTypes.CASH, order.getPayType())) {
            summaCash = toPaySum;
        } else if (Objects.equals(PayTypes.CARD, order.getPayType())) {
            summaCard = toPaySum;
        }

        if (Objects.equals(DocumentTypes.ORDER, order.getDocumentType())) {
            summaSale = order.getDocumentSum();
            summaOrderCash = summaCash;
            summaOrderCard = summaCard;
            summaSafe = ShiftResultService.getTotalSumSafe() + summaCash;
        } else if (Objects.equals(DocumentTypes.RETURN, order.getDocumentType())) {
            summaReturn = order.getDocumentSum();
            summaReturnCash = summaCash;
            summaSafe = ShiftResultService.getTotalSumSafe() - summaCash;
        }

        StatementParameters<Object> parameters = StatementParameters.build(
                AppProperties.currentUser.getGuid(),
                summaOrderCash,
                summaReturnCash,
                summaSafe,
                summaOrderCard,
                summaSale,
                summaReturn
        );

        return new DatabaseRequest(sql, parameters);
    }

    public static void saveOrderToBackupDir(Order order) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEMP_FILE_ORDER_BACKUP))) {
            oos.writeObject(order);
        } catch (Exception ex) {
            LOG.error("Error save order to backup dir", ex);
        }
    }

    public static Order loadOrderFromBackupDir() {
        Order order = new Order();

        if (!FilesUtils.fileExist(TEMP_FILE_ORDER_BACKUP)) {
            return order;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEMP_FILE_ORDER_BACKUP))) {
            order = (Order) ois.readObject();
            return order;
        } catch (Exception ex) {
            LOG.error("Error load order from backup dir", ex);
        }

        return order;
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
}
