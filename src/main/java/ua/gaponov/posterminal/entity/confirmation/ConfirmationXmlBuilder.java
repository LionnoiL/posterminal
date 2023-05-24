package ua.gaponov.posterminal.entity.confirmation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.dataexchange.ExchangeBuilder;
import ua.gaponov.posterminal.entity.DocumentTypes;
import ua.gaponov.posterminal.utils.XmlUtils;

import javax.xml.stream.XMLStreamException;

/**
 * @author Andriy Gaponov
 */
public class ConfirmationXmlBuilder implements ExchangeBuilder<Confirmation, XmlUtils> {

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmationXmlBuilder.class);

    public Confirmation create(XmlUtils processor) {
        Confirmation confirmation = new Confirmation();
        try {
            switch (processor.getIntegerAttribute("doc_type")) {
                case 0:
                    confirmation.setDocumentType(DocumentTypes.ORDER);
                    break;
                case 1:
                    confirmation.setDocumentType(DocumentTypes.RETURN);
                    break;
                default:
                    confirmation.setDocumentType(DocumentTypes.PAY);
            }
            confirmation.setDocumentGuid(processor.getStringAttribute("doc_id"));
        } catch (XMLStreamException ex) {
            LOG.error(ex.toString());
        }
        return confirmation;
    }
}
