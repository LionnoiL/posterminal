package ua.gaponov.posterminal.documents.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.database.DatabaseRequest;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.documents.DocumentTypes;
import ua.gaponov.posterminal.organization.Organization;
import ua.gaponov.posterminal.utils.FilesUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                DocumentTypes.ORDER.toString(),
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

    public static Order createOrderByOrganization(Order order, Organization organization){
        Order tempOrder = new Order();
        tempOrder.setCard(order.getCard());
        tempOrder.setPayType(order.getPayType());
        List<OrderDetail> details = order.getDetails();
        for (OrderDetail detail : details) {
            if (Objects.equals(detail.getProduct().getOrganization(), organization)){
                tempOrder.getDetails().add(detail);
            }
        }
        tempOrder.recalculateDocumentSum();
        return tempOrder;
    }
}
