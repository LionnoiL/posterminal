package ua.gaponov.posterminal.barcodes;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import ua.gaponov.posterminal.products.ProductService;
import ua.gaponov.posterminal.utils.XmlUtils;

/**
 *
 * @author wmcon
 */
public class BarcodeXmlBuilder {
    
    public static Barcode create(XmlUtils processor){
        Barcode barcode = new Barcode();
        try {
            barcode.setBarCodeValue(processor.getStringAttribute("ean_code"));
            barcode.setProduct(ProductService.getByGuid(processor.getStringAttribute("guid_product")));

        }
        catch (XMLStreamException ex) {
            Logger.getLogger(BarcodeXmlBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return barcode;
    }
}
