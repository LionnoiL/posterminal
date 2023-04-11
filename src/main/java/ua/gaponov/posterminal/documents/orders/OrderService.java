package ua.gaponov.posterminal.documents.orders;

import java.util.List;
import ua.gaponov.posterminal.database.SqlHelper;

/**
 *
 * @author gaponov
 */
public class OrderService {
    public static List<Order> getAll(){
       return new SqlHelper<Order>().getAll("SELECT * FROM orders", new OrderMapper());
    }
}
