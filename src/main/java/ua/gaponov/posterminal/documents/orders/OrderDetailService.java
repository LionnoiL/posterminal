package ua.gaponov.posterminal.documents.orders;

import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.sql.SQLException;
import java.util.List;

/**
 * @author wmcon
 */
public class OrderDetailService {

    private static final SqlHelper<OrderDetail> helper = new SqlHelper<>();

    public static List<OrderDetail> getByOrder(String orderGuid) {
        StatementParameters<String> parameters = StatementParameters.buildParameters(orderGuid);
        return helper.getAll("select * from orders_detail where order_guid = ?",
                parameters,
                new OrderDetailDatabaseMapper());
    }

    public static void deleteAll() {
        SqlHelper.execSql("delete from orders_detail");
    }

    public static void save(Order order, OrderDetail orderDetail, int line) throws SQLException {
        String sql = """                  
                    insert into orders_detail
                    (orders_detail_guid, order_guid, line_number, product_guid, qty,
                    price, summa_without_discount, summa_discount, summa, excise)
                    values
                    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?); 
                """;
        StatementParameters<Object> parameters = StatementParameters.buildParameters(
                orderDetail.getGuid(),
                order.getGuid(),
                line,
                orderDetail.getProduct().getGuid(),
                orderDetail.getQty(),
                orderDetail.getPrice(),
                orderDetail.getSummaWithoutDiscount(),
                orderDetail.getSummaDiscount(),
                orderDetail.getSumma(),
                orderDetail.getExcise()
        );
        helper.execSql(sql, parameters);
    }
}
