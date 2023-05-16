package ua.gaponov.posterminal.entity.barcodes;

import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;
import ua.gaponov.posterminal.entity.products.ProductService;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
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
