package ua.gaponov.posterminal.documents.orders;

import java.util.List;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

/**
 *
 * @author wmcon
 */
public class OrderDetailService {
    
    public static List<OrderDetail> getByOrder(String orderGuid){
        StatementParameters<String> parameters = StatementParameters.buildParameters(orderGuid);
        return new SqlHelper<OrderDetail>().getAll("select * from orders_detail where order_guid = ?",
                parameters,
                new OrderDetailDatabaseMapper());
    }
    public static void deleteAll(){
        SqlHelper.execSql("delete from orders_detail");
    }
}
