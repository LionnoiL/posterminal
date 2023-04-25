package ua.gaponov.posterminal.quickproduct;

import java.sql.SQLException;
import java.util.List;

import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.products.Product;

public class QuickProductService {

    public static final int buttonsCountOnPage = 42;

    public static List<QuickProduct> getByPage(int pageIndex) {
        StatementParameters<Integer> parameters = StatementParameters.buildParameters(
                buttonsCountOnPage,
                pageIndex * buttonsCountOnPage
        );

        String sql = """
            SELECT * FROM quick_products Order by QUICK_PRODUCTS.POS_ID  limit ? offset ?
            """;
        return new SqlHelper<QuickProduct>().getAll(sql,
            parameters,
            new QuickProductDatabaseMapper());
    }

    public static QuickProduct getByProduct(String guid) {
        StatementParameters<String> parameters = StatementParameters.buildParameters(guid);
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
        StatementParameters parameters = StatementParameters.buildParameters(
                quickProduct.getProduct().getGuid(),
                quickProduct.getPosition(),
                quickProduct.getColor()
        );
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
        StatementParameters parameters
            = StatementParameters.buildParameters(
                quickProduct.getPosition(),
                quickProduct.getColor(),
                quickProduct.getProduct().getGuid()
        );
        String sql = """
            update quick_products set pos_id= ?,
            color= ?
            where product_id = ?
            """;
        new SqlHelper<Product>().execSql(sql, parameters);
    }

    public static void deleteAll() {
        SqlHelper.execSql("delete from quick_products");
    }
}
