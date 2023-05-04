package ua.gaponov.posterminal.organization;

import ua.gaponov.posterminal.utils.XmlUtils;

import javax.xml.stream.XMLStreamException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Andriy Gaponov
 */
public class OrganizationXmlBuilder {

    public static Organization create(XmlUtils processor) {
        Organization organization = new Organization();
        try {
            organization.setGuid(processor.getStringAttribute("guid_org"));
            organization.setCode(processor.getStringAttribute("code"));
            organization.setName(processor.getStringAttribute("name"));

        } catch (XMLStreamException ex) {
            Logger.getLogger(OrganizationXmlBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return organization;
    }
}
