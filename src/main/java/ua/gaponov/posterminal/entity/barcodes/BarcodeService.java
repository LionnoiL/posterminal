package ua.gaponov.posterminal.entity.barcodes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BarcodeService {

    private static final Logger LOG = LoggerFactory.getLogger(BarcodeService.class);

    private static final SqlHelper<Barcode> SQL_HELPER = new SqlHelper<>();

    public static Barcode getByBarcode(String barcode) {
        StatementParameters<String> parameters = StatementParameters.build(barcode);
        return new SqlHelper<Barcode>().getOne("select * from eans where ean_code = ?",
                parameters,
                new BarcodeDatabaseMapper());
    }

    public static void save(Barcode barcode) {
        Barcode barcodeFound = getByBarcode(barcode.getBarCodeValue());
        if (barcodeFound == null) {
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
                    insert into eans (ean_code, product_guid)
                    values
                    (?, ?)
                    """;
            try {
                SQL_HELPER.execSql(sql, parameters);
            } catch (SQLException ex) {
                LOG.error("Insert ean {} failed. {}", barcode, ex);
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
                SQL_HELPER.execSql(sql, parameters);
            } catch (SQLException ex) {
                LOG.error("Update ean {} failed. {}", barcode, ex);
            }
        }
    }
}
