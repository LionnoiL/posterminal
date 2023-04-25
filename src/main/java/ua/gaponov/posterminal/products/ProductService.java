package ua.gaponov.posterminal.products;

import ua.gaponov.posterminal.AppProperties;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.sql.SQLException;

/**
 * @author wmcon
 */
public class ProductService {

    private static final ProductDatabaseMapper mapper = new ProductDatabaseMapper();
    private static final SqlHelper<Product> helper = new SqlHelper<>();
    private ProductService() {
    }

    public static Product getByGuid(String guid) {
        StatementParameters<String> parameters = StatementParameters.buildParameters(guid);
        return helper.getOne(
                "select * from products where product_guid = ?",
                parameters,
                mapper);
    }

    public static Product getByBarcode(String barcode) {
        if (barcode == null || barcode.isEmpty() || barcode.length() < 8) {
            return null;
        }
        Product product;
        if (isWeightBarcode(barcode)) {
            product = getProductFromWeightBarcode(barcode);
        } else {
            product = getProductFromOrdinaryBarcode(barcode);
        }
        return product;
    }

    private static boolean isWeightBarcode(String barcode) {
        String weightItemPrefix = AppProperties.weightItemPrefix;
        return weightItemPrefix.equals(barcode.substring(0, 2));
    }

    private static Product getProductFromWeightBarcode(String barcode) {
        int codeLength = 5;
        String sku = removeStartZero(barcode.substring(2, 2 + codeLength));
        String qtyString = removeStartZero(barcode.substring(2 + codeLength, 12));
        double qty = Double.valueOf(qtyString);
        Product product = getProductFromSku(sku);
        if (product != null) {
            product.setQty(qty / 1000);
        }
        return product;
    }

    private static String removeStartZero(String inputString) {
        for (int i = 0; i < inputString.length() - 1; i++) {
            if (!"0".equals(inputString.substring(i, i + 1))) {
                return inputString.substring(i, inputString.length());
            }
        }
        return "";
    }

    private static Product getProductFromOrdinaryBarcode(String barcode) {
        StatementParameters<String> parameters = StatementParameters.buildParameters(barcode);
        String sql = """
                select * from eans
                left join products on products.product_guid = eans.product_guid
                where eans.ean_code = ?
                """;
        Product product = helper.getOne(
                sql,
                parameters,
                mapper);
        if (product != null) {
            product.setQty(1);
        }
        return product;
    }

    public static Product getProductFromSku(String sku) {
        StatementParameters<String> parameters = StatementParameters.buildParameters(sku);
        String sql = """
                select * from products
                where sku = ?
                """;
        Product product = helper.getOne(
                sql,
                parameters,
                mapper);
        if (product != null) {
            product.setQty(1);
        }
        return product;
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
        StatementParameters<Object> parameters = StatementParameters.buildParameters(
                product.isNeedExcise(),
                product.getGuid(),
                product.getName(),
                product.getPrice(),
                product.isBanDisckount(),
                product.getCode(),
                product.getSku(),
                product.isWeight()
        );

        if (product.getOrganization() != null && !product.getOrganization().getGuid().isEmpty()) {
            parameters.addAll(product.getOrganization().getGuid());
        } else {
            parameters.addNull();
        }

        String sql = """
                insert into products (need_excise, product_guid, product_name, price,
                no_discount, product_code, sku, weight, org_guid
                )
                values
                (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        helper.execSql(sql, parameters);
    }

    private static void update(Product product) throws SQLException {
        StatementParameters<Object> parameters = StatementParameters.buildParameters(
                product.getName(),
                product.getPrice(),
                product.isBanDisckount(),
                product.getCode(),
                product.getSku(),
                product.isWeight(),
                product.isNeedExcise());

        if (product.getOrganization() != null && !product.getOrganization().getGuid().isEmpty()) {
            parameters.addAll(product.getOrganization().getGuid());
        } else {
            parameters.addNull();
        }

        parameters.addAll(product.getGuid());
        String sql = """
                update products set product_name= ?,
                price= ?,
                no_discount = ?,
                product_code = ?,
                sku = ?,
                weight = ?,
                need_excise = ?,
                org_guid = ?
                where product_guid = ?
                """;
        helper.execSql(sql, parameters);
    }
}
