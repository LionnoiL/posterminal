package ua.gaponov.posterminal.products;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;
import ua.gaponov.posterminal.organization.OrganizationService;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
public class ProductDatabaseMapper implements Mapper<Product> {

    private static final Logger LOG = LoggerFactory.getLogger(ProductDatabaseMapper.class);

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
            product.setOrganization(OrganizationService.getByGuid(rs.getString("org_guid")));
            product.setProstoPayProduct(rs.getBoolean("prostopay_product"));

            return product;
        } catch (SQLException ex) {
            LOG.error("Error map product", ex);
            new MapperException("Error map product");
        }
        return null;
    }
}
