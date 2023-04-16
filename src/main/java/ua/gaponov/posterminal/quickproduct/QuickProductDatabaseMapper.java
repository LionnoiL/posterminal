package ua.gaponov.posterminal.quickproduct;

import java.sql.ResultSet;
import java.sql.SQLException;
import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.products.ProductService;

public class QuickProductDatabaseMapper implements Mapper<QuickProduct> {

    @Override
    public QuickProduct map(ResultSet rs) {
        try {
            QuickProduct product = new QuickProduct();
            product.setPosition(Integer.parseInt(rs.getString("pos_id")));
            product.setColor(rs.getString("color"));
            product.setProduct(
                ProductService.getByGuid(rs.getString("product_id"))
            );

            return product;
        } catch (SQLException e) {
            new RuntimeException();
        }
        return null;
    }
}
