package ua.gaponov.posterminal.documents.orders;

import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.documents.DocumentTypes;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

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
        insert(order);
    }

    private static void insert(Order order) throws SQLException {
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

        if (Objects.nonNull(order.getCard())){
            parameters.addAll(order.getCard().getGuid());
        } else {
          parameters.addNull();
        }
        helper.execSql(sql, parameters);

        List<OrderDetail> details = order.getDetails();
        int line = 1;
        for (OrderDetail detail : details) {
            OrderDetailService.save(order, detail, line);
            line = line + 1;
        }
    }
}
