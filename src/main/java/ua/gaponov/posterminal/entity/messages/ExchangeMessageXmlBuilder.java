package ua.gaponov.posterminal.entity.messages;

import ua.gaponov.posterminal.dataexchange.ExchangeBuilder;
import ua.gaponov.posterminal.utils.XmlUtils;

import javax.xml.stream.XMLStreamException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Andriy Gaponov
 */
public class ExchangeMessageXmlBuilder implements ExchangeBuilder<ExchangeMessage, XmlUtils> {

    public ExchangeMessage create(XmlUtils processor) {
        ExchangeMessage message = new ExchangeMessage();
        try {
            ExchangeMessage savedMessage = ExchangeMessageService.getMessages();
            message.setReceivedNumber(savedMessage.getReceivedNumber());
            message.setTakenNumber(processor.getIntegerAttribute("received"));
        } catch (XMLStreamException ex) {
            Logger.getLogger(ExchangeMessageXmlBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }
}
