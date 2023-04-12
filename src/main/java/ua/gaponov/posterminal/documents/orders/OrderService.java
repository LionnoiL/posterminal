package ua.gaponov.posterminal.documents.orders;

import java.util.List;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

/**
 *
 * @author gaponov
 */
public class OrderService {
    
    public static Order getByGuid(String guid){
        StatementParameters<String, String> parameters = new StatementParameters<>(guid);
        return new SqlHelper<Order>().getOne("select * from orders where order_guid = ?",
                parameters,
                new OrderMapper());
    }

    public static List<Order> getAll() {
        return new SqlHelper<Order>().getAll("SELECT * FROM orders", new OrderMapper());
    }

    public static List<Order> getAllNoUpload() {
        StatementParameters<Boolean, String> parameters = new StatementParameters<>(false);
        return new SqlHelper<Order>().getAll("SELECT * FROM orders where upload = ?",
                parameters,
                new OrderMapper());
    }
    
    public static void deleteAll(){
        SqlHelper.execSql("delete from orders");
    }
    
    public static void deleteUploaded(){
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
}
