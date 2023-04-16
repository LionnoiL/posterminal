package ua.gaponov.posterminal.quickproduct;

import java.sql.SQLException;
import ua.gaponov.posterminal.database.Parametr;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.products.Product;

public class QuickProductService {

    public static QuickProduct getByProduct(String guid) {
        StatementParameters<String, String> parameters = new StatementParameters<>(guid);
        return new SqlHelper<QuickProduct>().getOne(
            "select * from quick_products where product_id = ?",
            parameters,
            new QuickProductDatabaseMapper());
    }

    public static void save(QuickProduct quickProduct) throws SQLException {
        QuickProduct findProduct = getByProduct(quickProduct.getProduct().getGuid());
        if (findProduct == null) {
            insert(quickProduct);
        } else {
            update(quickProduct);
        }
    }

    private static void insert(QuickProduct quickProduct) throws SQLException {
        if (quickProduct.getProduct() == null) {
            return;
        }
        StatementParameters<String, Integer> parameters
            = new StatementParameters<>(
            quickProduct.getProduct().getGuid(),
            quickProduct.getPosition()
        );
        parameters.add(new Parametr(quickProduct.getColor()));

        String sql = """
            insert into quick_products (product_id, pos_id, color) 
            values
            (?, ?, ?)
            """;
        new SqlHelper<QuickProduct>().execSql(sql, parameters);
    }

    private static void update(QuickProduct quickProduct) throws SQLException {
        if (quickProduct.getProduct() == null) {
            return;
        }
        StatementParameters<Integer, String> parameters
            = new StatementParameters<>(
            quickProduct.getPosition(),
            quickProduct.getColor()
        );
        parameters.add(new Parametr(quickProduct.getProduct().getGuid()));
        String sql = """
            update quick_products set pos_id= ?,
            color= ?
            where product_id = ?
            """;
        new SqlHelper<Product>().execSql(sql, parameters);
    }
}
