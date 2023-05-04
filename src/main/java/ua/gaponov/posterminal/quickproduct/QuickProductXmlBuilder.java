package ua.gaponov.posterminal.quickproduct;

import ua.gaponov.posterminal.products.ProductService;
import ua.gaponov.posterminal.products.ProductXmlBuilder;
import ua.gaponov.posterminal.utils.XmlUtils;

import javax.xml.stream.XMLStreamException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Andriy Gaponov
 */
public class QuickProductXmlBuilder {

    public static QuickProduct create(XmlUtils processor) {
        QuickProduct quickProduct = new QuickProduct();
        try {
            quickProduct.setProduct(
                    ProductService.getByGuid(processor.getStringAttribute("product_id")));
            quickProduct.setColor(processor.getStringAttribute("color"));
            quickProduct.setPosition(processor.getIntegerAttribute("pos_id"));
        } catch (XMLStreamException ex) {
            Logger.getLogger(ProductXmlBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return quickProduct;
    }
}
