package ua.gaponov.posterminal.documents.orders;

import ua.gaponov.posterminal.database.DatabaseRequest;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.util.List;
import java.util.Objects;

/**
 * @author Andriy Gaponov
 */
public class OrderDetailService {

    private static final SqlHelper<OrderDetail> helper = new SqlHelper<>();

    public static List<OrderDetail> getByOrder(String orderGuid) {
        StatementParameters<String> parameters = StatementParameters.build(orderGuid);
        return helper.getAll("select * from orders_detail where order_guid = ?",
                parameters,
                new OrderDetailDatabaseMapper());
    }

    public static void deleteAll() {
        SqlHelper.execSql("delete from orders_detail");
    }

    public static DatabaseRequest getInsertRequest(Order order, OrderDetail orderDetail, int line) {
        String sqlInsertOrderDetail = """                  
                    insert into orders_detail
                    (orders_detail_guid, order_guid, line_number, product_guid, qty,
                    price, summa_without_discount, summa_discount, summa, excise, fiscal_print, org_guid)
                    values
                    (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        StatementParameters<Object> sqlInsertParameters = StatementParameters.build(
                orderDetail.getGuid(),
                order.getGuid(),
                line,
                orderDetail.getProduct().getGuid(),
                orderDetail.getQty(),
                orderDetail.getPrice(),
                orderDetail.getSummaWithoutDiscount(),
                orderDetail.getSummaDiscount(),
                orderDetail.getSumma(),
                orderDetail.getExcise(),
                orderDetail.isFiscalPrint()
        );

        if (Objects.nonNull(orderDetail.getOrganization())) {
            sqlInsertParameters.addAll(orderDetail.getOrganization().getGuid());
        } else {
            sqlInsertParameters.addNull();
        }

        return new DatabaseRequest(sqlInsertOrderDetail, sqlInsertParameters);
    }
}
