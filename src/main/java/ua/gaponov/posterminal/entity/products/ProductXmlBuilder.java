package ua.gaponov.posterminal.entity.products;

import ua.gaponov.posterminal.entity.organization.OrganizationService;
import ua.gaponov.posterminal.utils.XmlUtils;

import javax.xml.stream.XMLStreamException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Andriy Gaponov
 */
public class ProductXmlBuilder {

    public static Product create(XmlUtils processor) {
        Product product = new Product();
        try {
            product.setGuid(processor.getStringAttribute("guid_product"));
            product.setCode(processor.getStringAttribute("code"));
            product.setName(processor.getStringAttribute("name"));
            product.setPrice(processor.getDoubleAttribute("price"));
            product.setBanDisckount(processor.getBooleanAttribute("no_discount"));
            product.setWeight(processor.getBooleanAttribute("weight"));
            product.setNeedExcise(processor.getBooleanAttribute("need_excise"));
            product.setSku(processor.getStringAttribute("sku"));
            product.setOrganization(OrganizationService.getByGuid(processor.getStringAttribute("guid_org")));
            product.setUnitName(processor.getStringAttribute("unit_name"));
            product.setProstoPayProduct(processor.getBooleanAttribute("prostopay_product"));
            product.setTaxGroup(processor.getIntegerAttribute("tax_group"));
        } catch (XMLStreamException ex) {
            Logger.getLogger(ProductXmlBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return product;
    }


}
