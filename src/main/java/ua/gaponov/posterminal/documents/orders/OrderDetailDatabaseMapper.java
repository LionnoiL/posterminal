package ua.gaponov.posterminal.documents.orders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;
import ua.gaponov.posterminal.organization.OrganizationService;
import ua.gaponov.posterminal.products.ProductService;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
public class OrderDetailDatabaseMapper implements Mapper<OrderDetail> {

    private static final Logger LOG = LoggerFactory.getLogger(OrderDatabaseMapper.class);
    @Override
    public OrderDetail map(ResultSet rs) {
        try {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setGuid(rs.getString("orders_detail_guid"));
            orderDetail.setLineNumber(rs.getInt("line_number"));
            orderDetail.setProduct(ProductService.getByGuid(rs.getString("product_guid")));
            orderDetail.setQty(rs.getDouble("qty"));
            orderDetail.setPrice(rs.getDouble("price"));
            orderDetail.setSumma(rs.getDouble("summa"));
            orderDetail.setSummaWithoutDiscount(rs.getDouble("summa_without_discount"));
            orderDetail.setSummaDiscount(rs.getDouble("summa_discount"));
            orderDetail.setExcise(rs.getString("excise"));
            orderDetail.setOrganization(OrganizationService.getByGuid(rs.getString("org_guid")));
            orderDetail.setFiscalPrint(rs.getBoolean("fiscal_print"));
            return orderDetail;
        } catch (SQLException ex) {
            LOG.error("Mapping order detail failed", ex);
            new MapperException("Error map order detail");
        }
        return null;
    }

}
