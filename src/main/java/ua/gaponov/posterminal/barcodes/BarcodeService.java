package ua.gaponov.posterminal.barcodes;

import java.sql.SQLException;
import java.util.List;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

/**
 *
 * @author wmcon
 */
public class BarcodeService {

    public static List<Barcode> getAll() {
        return new SqlHelper<Barcode>().getAll("SELECT * FROM eans", new BarcodeDatabaseMapper());
    }

    public static Barcode getByBarcode(String barcode) {
        StatementParameters<String, String> parameters = new StatementParameters<>(barcode);
        return new SqlHelper<Barcode>().getOne("select * from eans where ean_code = ?",
                parameters,
                new BarcodeDatabaseMapper());
    }

    public static void deleteAll() {
        SqlHelper.execSql("delete from eans");
    }

    public static void save(Barcode barcode) throws SQLException {
        Barcode findBarcode = getByBarcode(barcode.getBarcode());
        if (findBarcode == null) {
            insert(barcode);
        } else {
            update(barcode);
        }
    }

    private static void insert(Barcode barcode) {
        if (barcode.getProduct() != null && !barcode.getBarcode().isEmpty()) {
            StatementParameters<String, String> parameters
                    = new StatementParameters<>(barcode.getBarcode(), barcode.getProduct().getGuid());
            String sql = """
                     insert into eans (ean_code, product_guid
                     ) 
                     values
                     (?, ?)
                     """;
            try {
                new SqlHelper<Barcode>().execSql(sql, parameters);
            }
            catch (SQLException ex) {
                //TODO проблема з одинаковыми штрихкодами
                //NOP
            }
        }
    }

    private static void update(Barcode barcode) {
        if (barcode.getProduct() != null && !barcode.getBarcode().isEmpty()) {
            StatementParameters<String, String> parameters
                    = new StatementParameters<>(barcode.getProduct().getGuid(), barcode.getBarcode());
            String sql = """
                     update eans set product_guid= ?,
                     where ean_code = ?
                     """;
            try {
                new SqlHelper<Barcode>().execSql(sql, parameters);
            }
            catch (SQLException ex) {
                //TODO проблема з одинаковыми штрихкодами
                //NOP
            }
        }
    }
}
