package ua.gaponov.posterminal.entity.barcodes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BarcodeService {

    public static Barcode getByBarcode(String barcode) {
        StatementParameters<String> parameters = StatementParameters.build(barcode);
        return new SqlHelper<Barcode>().getOne("select * from eans where ean_code = ?",
                parameters,
                new BarcodeDatabaseMapper());
    }

    public static void save(Barcode barcode) {
        Barcode findBarcode = getByBarcode(barcode.getBarCodeValue());
        if (findBarcode == null) {
            insert(barcode);
        } else {
            update(barcode);
        }
    }

    private static void insert(Barcode barcode) {
        if (barcode.getProduct() != null && !barcode.getBarCodeValue().isEmpty()) {
            StatementParameters<String> parameters = StatementParameters.build(
                    barcode.getBarCodeValue(),
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
                Logger.getLogger(BarcodeService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void update(Barcode barcode) {
        if (barcode.getProduct() != null && !barcode.getBarCodeValue().isEmpty()) {
            StatementParameters<String> parameters = StatementParameters.build(
                    barcode.getProduct().getGuid(),
                    barcode.getBarCodeValue()
            );
            String sql = """
                    update eans set product_guid= ?
                    where ean_code = ?
                    """;
            try {
                new SqlHelper<Barcode>().execSql(sql, parameters);
            } catch (SQLException ex) {
                Logger.getLogger(BarcodeService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
