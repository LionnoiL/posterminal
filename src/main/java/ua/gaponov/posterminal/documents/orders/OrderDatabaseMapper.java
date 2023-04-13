package ua.gaponov.posterminal.documents.orders;

import ua.gaponov.posterminal.documents.PayTypes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import ua.gaponov.posterminal.cards.CardService;
import ua.gaponov.posterminal.database.Mapper;

/**
 *
 * @author gaponov
 */
public class OrderDatabaseMapper implements Mapper<Order>{

    @Override
    public Order map(ResultSet rs) {
        try {
            Order order = new Order();
            order.setGuid(rs.getString("order_guid"));
            order.setDate(rs.getTimestamp("order_date").toLocalDateTime());
            order.setCard(CardService.getByGuid(rs.getString("card_guid")));
            order.setDocumentSum(rs.getDouble("summa_doc"));
            order.setPaySum(rs.getDouble("summa_pay"));
            order.setFiscal(rs.getBoolean("fiscal"));
            order.setInternet(rs.getBoolean("internet"));
            order.setFiscalPrint(rs.getBoolean("fiscal_print"));
            order.setUpload(rs.getBoolean("upload"));
            order.setPayType(PayTypes.valueOf(rs.getString("pay_type")));
            
            List<OrderDetail> details = OrderDetailService.getByOrder(order.getGuid());
            order.setDetails(details);

            return order;
        } catch (SQLException e) {
            new RuntimeException();
        }
        return null;
    }
    
}
