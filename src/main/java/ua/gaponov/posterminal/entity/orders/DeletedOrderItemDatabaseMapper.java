package ua.gaponov.posterminal.entity.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
public class DeletedOrderItemDatabaseMapper implements Mapper<DeletedOrderItem> {

    private static final Logger LOG = LoggerFactory.getLogger(DeletedOrderItemDatabaseMapper.class);

    @Override
    public DeletedOrderItem map(ResultSet rs) {
        try {
            DeletedOrderItem item = new DeletedOrderItem();
            item.setUserName(rs.getString("user_name"));
            item.setDeletedTime(rs.getTimestamp("delete_date_time").toLocalDateTime());
            item.setProductName(rs.getString("product_name"));
            item.setQty(rs.getDouble("qty"));
            item.setSumma(rs.getDouble("summa"));
            return item;
        } catch (SQLException ex) {
            LOG.error("Mapping deleted order item failed", ex);
            new MapperException("Error map deleted order item");
        }
        return null;
    }
}
