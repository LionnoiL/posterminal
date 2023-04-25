package ua.gaponov.posterminal.barcodes;

import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.sql.SQLException;

/**
 * @author wmcon
 */
public class BarcodeService {

    private BarcodeService() {
    }

    public static Barcode getByBarcode(String barcode) {
        StatementParameters<String> parameters = StatementParameters.buildParameters(barcode);
        return new SqlHelper<Barcode>().getOne("select * from eans where ean_code = ?",
                parameters,
                new BarcodeDatabaseMapper());
    }

    public static void save(Barcode barcode) {
        Barcode findBarcode = getByBarcode(barcode.getBarcode());
        if (findBarcode == null) {
            insert(barcode);
        } else {
            update(barcode);
        }
    }

    private static void insert(Barcode barcode) {
        if (barcode.getProduct() != null && !barcode.getBarcode().isEmpty()) {
            StatementParameters<String> parameters = StatementParameters.buildParameters(
                    barcode.getBarcode(),
                    barcode.getProduct().getGuid()
            );
            String sql = """
                    insert into eans (ean_code, product_guid
                    )
                    values
                    (?, ?)
                    """;
            try {
                new SqlHelper<Barcode>().execSql(sql, parameters);
            } catch (SQLException ex) {
                //TODO проблема з одинаковыми штрихкодами
                //NOP
            }
        }
    }

    private static void update(Barcode barcode) {
        if (barcode.getProduct() != null && !barcode.getBarcode().isEmpty()) {
            StatementParameters<String> parameters = StatementParameters.buildParameters(
                    barcode.getProduct().getGuid(),
                    barcode.getBarcode()
            );
            String sql = """
                    update eans set product_guid= ?,
                    where ean_code = ?
                    """;
            try {
                new SqlHelper<Barcode>().execSql(sql, parameters);
            } catch (SQLException ex) {
                //TODO проблема з одинаковыми штрихкодами
                //NOP
            }
        }
    }
}
