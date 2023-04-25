package ua.gaponov.posterminal.documents.orders;

import java.sql.SQLException;
import java.util.List;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

/**
 * @author gaponov
 */
public class OrderService {

    private static final SqlHelper<Order> helper = new SqlHelper<>();

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
                SET REFERENTIAL_INTEGRITY FALSE;
                BEGIN TRANSACTION;
                DELETE FROM orders_detail WHERE order_guid IN (SELECT order_guid FROM orders WHERE upload = true);
                DELETE FROM orders WHERE upload = true;
                COMMIT;
                SET REFERENTIAL_INTEGRITY TRUE;
            """;
        SqlHelper.execSql(sql);
    }

    public static void save(Order order) throws SQLException {
        Order findOrder = getByGuid(order.getGuid());
        if (findOrder == null) {
            insert(order);
        } else {
            update(order);
        }
    }

    private static void update(Order order) {
        String sql = """
                SET REFERENTIAL_INTEGRITY FALSE;
                BEGIN TRANSACTION;
                
                COMMIT;
                SET REFERENTIAL_INTEGRITY TRUE;
            """;
        SqlHelper.execSql(sql);
    }

    private static void insert(Order order) {
        String sql = """
                SET REFERENTIAL_INTEGRITY FALSE;
                BEGIN TRANSACTION;
                
                COMMIT;
                SET REFERENTIAL_INTEGRITY TRUE;
            """;
        SqlHelper.execSql(sql);
    }
}
