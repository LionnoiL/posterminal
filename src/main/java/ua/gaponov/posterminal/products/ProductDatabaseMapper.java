package ua.gaponov.posterminal.products;

import java.sql.ResultSet;
import java.sql.SQLException;
import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.organization.OrganizationService;

/**
 *
 * @author wmcon
 */
public class ProductDatabaseMapper implements Mapper<Product>{
    
    @Override
    public Product map(ResultSet rs) {
        try {
            Product product = new Product();
            product.setGuid(rs.getString("product_guid"));
            product.setName(rs.getString("product_name"));
            product.setCode(rs.getString("product_code"));
            product.setBanDisckount(rs.getBoolean("no_discount"));
            product.setPrice(rs.getDouble("price"));
            product.setSku(rs.getString("sku"));
            product.setTaxCode(rs.getString("uktved"));
            product.setTaxGroup(rs.getInt("tax_group"));
            product.setWeight(rs.getBoolean("weight"));
            product.setNeedExcise(rs.getBoolean("need_excise"));
            product.setUnitName(rs.getString("unit_name"));
            product.setOrganization(
                OrganizationService.getByGuid(rs.getString("org_guid"))
            );
            
            return product;
        } catch (SQLException e) {
            new RuntimeException();
        }
        return null;
    }
}
