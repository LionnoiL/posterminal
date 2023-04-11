package ua.gaponov.posterminal.barcodes;

import java.sql.ResultSet;
import java.sql.SQLException;
import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.products.ProductService;

/**
 *
 * @author wmcon
 */
public class BarcodeMapper implements Mapper<Barcode> {

    @Override
    public Barcode map(ResultSet rs) {
        try {
            Barcode barcode = new Barcode();
            barcode.setBarcode(rs.getString("ean_code"));
            barcode.setProduct(ProductService.getByGuid(rs.getString("org_name")));

            return barcode;
        } catch (SQLException e) {
            new RuntimeException();
        }
        return null;
    }

}
