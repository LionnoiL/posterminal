package ua.gaponov.posterminal.barcodes;

import java.util.List;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

/**
 *
 * @author wmcon
 */
public class BarcodeService {
    public static List<Barcode> getAll(){
       return new SqlHelper<Barcode>().getAll("SELECT * FROM eans", new BarcodeMapper());
    }
    
    public static Barcode getByBarcode(String barcode){
        StatementParameters<String, String> parameters = new StatementParameters<>(barcode);
        return new SqlHelper<Barcode>().getOne("select * from eans where ean_code = ?",
                parameters,
                new BarcodeMapper());
    }
    
    public static void deleteAll(){
        SqlHelper.execSql("delete from eans");
    }
}
