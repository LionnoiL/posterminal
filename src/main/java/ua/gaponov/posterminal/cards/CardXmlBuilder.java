package ua.gaponov.posterminal.cards;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import ua.gaponov.posterminal.utils.XmlUtils;

/**
 * @author wmcon
 */
public class CardXmlBuilder {

    public static Card create(XmlUtils processor) {
        Card card = new Card();
        try {
            card.setGuid(processor.getStringAttribute("guid_card"));
            card.setCode(processor.getStringAttribute("code"));
            card.setActive(processor.getBooleanAttribute("active"));

            if ("1".equals(processor.getStringAttribute("card_type"))) {
                card.setCardType(CardType.DISCOUNT);
            } else {
                card.setCardType(CardType.SERVICE);
            }

            card.setClientName(processor.getStringAttribute("client_name"));
            card.setClientPhone(processor.getStringAttribute("client_phone"));
            card.setClientEmail(processor.getStringAttribute("client_email"));
            card.setDiscount(processor.getDoubleAttribute("discount"));
            card.setDebt(processor.getDoubleAttribute("debt"));
            card.setDebtAllowed(processor.getBooleanAttribute("debt_allowed"));
            card.setMaxDebt(processor.getDoubleAttribute("max_debt"));
            card.setMaxDebtDay(processor.getIntegerAttribute("max_debt_day"));
        } catch (XMLStreamException ex) {
            Logger.getLogger(CardXmlBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return card;
    }
}
