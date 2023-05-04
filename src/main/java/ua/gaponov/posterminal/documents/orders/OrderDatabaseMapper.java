package ua.gaponov.posterminal.documents.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.cards.CardService;
import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;
import ua.gaponov.posterminal.documents.PayTypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Andriy Gaponov
 */
public class OrderDatabaseMapper implements Mapper<Order> {

    private static final Logger LOG = LoggerFactory.getLogger(OrderDatabaseMapper.class);

    @Override
    public Order map(ResultSet rs) {
        try {
            Order order = new Order();
            order.setGuid(rs.getString("order_guid"));
            order.setDate(rs.getTimestamp("order_date").toLocalDateTime());
            order.setCard(CardService.getByGuid(rs.getString("card_guid")));
            order.setDocumentSum(rs.getDouble("summa_doc"));
            order.setPaySum(rs.getDouble("summa_pay"));
            order.setToPaySum(rs.getDouble("summa_to_pay"));
            order.setDiscountSum(rs.getDouble("summa_discount"));
            order.setFiscal(rs.getBoolean("fiscal"));
            order.setInternet(rs.getBoolean("internet"));
            order.setFiscalPrint(rs.getBoolean("fiscal_print"));
            order.setUpload(rs.getBoolean("upload"));
            order.setPayType(PayTypes.valueOf(rs.getString("pay_type")));
            order.setOrderNumber(rs.getLong("order_number"));

            List<OrderDetail> details = OrderDetailService.getByOrder(order.getGuid());
            order.setDetails(details);

            return order;
        } catch (SQLException ex) {
            LOG.error("Mapping order failed", ex);
            new MapperException("Error map order");
        }
        return null;
    }

}
