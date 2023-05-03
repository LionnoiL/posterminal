package ua.gaponov.posterminal.barcodes;

import java.sql.ResultSet;
import java.sql.SQLException;
import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;
import ua.gaponov.posterminal.products.ProductService;

/**
 *
 * @author wmcon
 */
public class BarcodeDatabaseMapper implements Mapper<Barcode> {

    @Override
    public Barcode map(ResultSet rs) {
        try {
            Barcode barcode = new Barcode();
            barcode.setBarCodeValue(rs.getString("ean_code"));
            barcode.setProduct(ProductService.getByGuid(rs.getString("PRODUCT_GUID")));

            return barcode;
        } catch (SQLException e) {
            new MapperException("Error map barcode");
        }
        return null;
    }

}
