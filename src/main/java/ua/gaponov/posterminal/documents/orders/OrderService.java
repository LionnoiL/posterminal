package ua.gaponov.posterminal.documents.orders;

import ua.gaponov.posterminal.database.DatabaseRequest;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.documents.DocumentTypes;
import ua.gaponov.posterminal.utils.FilesUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author gaponov
 */
public class OrderService {

    private static final SqlHelper<Order> helper = new SqlHelper<>();
    private static final String TEMP_FILE_ORDER_BACKUP = "files/temp-order.dat";

    public static Order getByGuid(String guid) {
        StatementParameters<String> parameters = StatementParameters.buildParameters(guid);
        return helper.getOne("select * from orders where order_guid = ?",
                parameters,
                new OrderDatabaseMapper());
    }

    public static List<Order> getAll() {
        return helper.getAll("SELECT * FROM orders", new OrderDatabaseMapper());
    }

    public static List<Order> getAllNoUpload() {
        StatementParameters<Boolean> parameters = StatementParameters.buildParameters(false);
        return helper.getAll("SELECT * FROM orders where upload = ?",
                parameters,
                new OrderDatabaseMapper());
    }

    public static void deleteAll() {
        SqlHelper.execSql("delete from orders");
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
        String sql = """
                    insert into orders
                    (order_guid, summa_doc, summa_pay, doc_type, pay_type, upload, fiscal, internet, fiscal_print, card_guid)
                    values
                    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        StatementParameters<Object> parameters = StatementParameters.buildParameters(
                order.getGuid(),
                order.getDocumentSum(),
                order.getPaySum(),
                DocumentTypes.ORDER.toString(),
                order.getPayType().toString(),
                false,
                false,
                false,
                false
        );//TODO fiscal_print and fiscal fix

        if (Objects.nonNull(order.getCard())) {
            parameters.addAll(order.getCard().getGuid());
        } else {
            parameters.addNull();
        }

        return new DatabaseRequest(sql, parameters);
    }

    public static void saveOrderToBackupDir(Order order) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEMP_FILE_ORDER_BACKUP))) {
            oos.writeObject(order);
        } catch (Exception ex) {
            Logger.getLogger(OrderService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Order loadOrderFromBackupDir() {
        Order order = new Order();

        if (!FilesUtils.fileExist(TEMP_FILE_ORDER_BACKUP)){
            return order;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEMP_FILE_ORDER_BACKUP))) {
            order = (Order) ois.readObject();
            return order;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return order;
    }
}
