package ua.gaponov.posterminal.products;

import java.util.List;
import ua.gaponov.posterminal.barcodes.BarcodeService;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

/**
 *
 * @author wmcon
 */
public class ProductService {
    
    public static List<Product> getAll(){
       return new SqlHelper<Product>().getAll("SELECT * FROM products", new ProductMapper());
    }
    
    public static Product getByGuid(String guid){
        StatementParameters<String, String> parameters = new StatementParameters<>(guid);
        return new SqlHelper<Product>().getOne("select * from products where product_guid = ?",
                parameters,
                new ProductMapper());
    }
    
    public static Product getBybarcode(String barcode){
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
    
    public static void deleteAll(){
        BarcodeService.deleteAll();
        SqlHelper.execSql("delete from products");
    }
}
