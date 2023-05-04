package ua.gaponov.posterminal.quickproduct;

import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.products.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Andriy Gaponov
 */
public class QuickProductService {

    public static final int BUTTONS_COUNT_ON_PAGE = 42;

    private QuickProductService() {

    }

    public static List<QuickProduct> getByPage(int pageIndex) {
        StatementParameters<Integer> parameters = StatementParameters.build(
                BUTTONS_COUNT_ON_PAGE,
                pageIndex * BUTTONS_COUNT_ON_PAGE
        );

        String sql = """
                SELECT * FROM quick_products Order by QUICK_PRODUCTS.POS_ID  limit ? offset ?
                """;
        return new SqlHelper<QuickProduct>().getAll(sql,
                parameters,
                new QuickProductDatabaseMapper());
    }

    public static QuickProduct getByProduct(String guid) {
        StatementParameters<String> parameters = StatementParameters.build(guid);
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
        StatementParameters<Object> parameters = StatementParameters.build(
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
        StatementParameters<Object> parameters
                = StatementParameters.build(
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
