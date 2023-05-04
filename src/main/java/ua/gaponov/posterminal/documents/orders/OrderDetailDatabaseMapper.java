package ua.gaponov.posterminal.documents.orders;

import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;
import ua.gaponov.posterminal.products.ProductService;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
public class OrderDetailDatabaseMapper implements Mapper<OrderDetail> {

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
            return orderDetail;
        } catch (SQLException e) {
            new MapperException("Error map order detail");
        }
        return null;
    }

}
