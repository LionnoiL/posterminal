package ua.gaponov.posterminal.products;

import java.sql.SQLException;
import java.util.List;
import ua.gaponov.posterminal.barcodes.BarcodeService;
import ua.gaponov.posterminal.database.Parametr;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

/**
 *
 * @author wmcon
 */
public class ProductService {

    public static List<Product> getAll() {
        return new SqlHelper<Product>().getAll("SELECT * FROM products", new ProductMapper());
    }

    public static Product getByGuid(String guid) {
        StatementParameters<String, String> parameters = new StatementParameters<>(guid);
        return new SqlHelper<Product>().getOne("select * from products where product_guid = ?",
                parameters,
                new ProductMapper());
    }

    public static Product getBybarcode(String barcode) {
        StatementParameters<String, String> parameters = new StatementParameters<>(barcode);
        String sql = """
                     select * from eans 
                     left join products on products.product_guid = eans.product_guid
                     where eans.ean_code = ?
                     """;
        return new SqlHelper<Product>().getOne(sql,
                parameters,
                new ProductMapper());
    }

    public static void deleteAll() {
        BarcodeService.deleteAll();
        SqlHelper.execSql("delete from products");
    }

    public static void save(Product product) throws SQLException {
        Product findProduct = getByGuid(product.getGuid());
        if (findProduct == null) {
            insert(product);
        } else {
            update(product);
        }
    }

    private static void insert(Product product) throws SQLException {
        StatementParameters<String, String> parameters
                = new StatementParameters<>(
                        product.getGuid(),
                        product.getName()
                );
        parameters.add(new Parametr(product.getPrice()));
        parameters.add(new Parametr(product.isBanDisckount()));
        parameters.add(new Parametr(product.getCode()));
        parameters.add(new Parametr(product.getSku()));
        parameters.add(new Parametr(product.isWeight()));

        if (product.getOrganization() != null && !product.getOrganization().getGuid().isEmpty()) {
            parameters.add(new Parametr(product.getOrganization().getGuid()));
        } else {
            parameters.add(new Parametr(null));
        }

        String sql = """
                     insert into products (product_guid, product_name, price,
                     no_discount, product_code, sku, weight, org_guid
                     ) 
                     values
                     (?, ?, ?, ?, ?, ?, ?, ?)
                     """;
        new SqlHelper<Product>().execSql(sql, parameters);
    }

    private static void update(Product product) throws SQLException {
        StatementParameters<String, Double> parameters
                = new StatementParameters<>(
                        product.getName(),
                        product.getPrice()
                );
        parameters.add(new Parametr(product.isBanDisckount()));
        parameters.add(new Parametr(product.getCode()));
        parameters.add(new Parametr(product.getSku()));
        parameters.add(new Parametr(product.isWeight()));

        if (product.getOrganization() != null && !product.getOrganization().getGuid().isEmpty()) {
            parameters.add(new Parametr(product.getOrganization().getGuid()));
        } else {
            parameters.add(new Parametr(null));
        }

        parameters.add(new Parametr(product.getGuid()));
        String sql = """
                     update products set product_name= ?,
                     price= ?,
                     no_discount = ?,
                     product_code = ?,
                     sku = ?,
                     weight = ?,
                     org_guid = ?
                     where product_guid = ?
                     """;
        new SqlHelper<Product>().execSql(sql, parameters);
    }
}
