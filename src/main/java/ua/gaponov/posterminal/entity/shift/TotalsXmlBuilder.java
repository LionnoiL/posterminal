package ua.gaponov.posterminal.entity.shift;

import ua.gaponov.posterminal.dataexchange.ExchangeBuilder;
import ua.gaponov.posterminal.utils.DateUtils;
import ua.gaponov.posterminal.utils.XmlUtils;

import javax.xml.stream.XMLStreamException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Andriy Gaponov
 */
public class TotalsXmlBuilder implements ExchangeBuilder<ShiftResult1C, XmlUtils> {

    public ShiftResult1C create(XmlUtils processor) {
        ShiftResult1C total = new ShiftResult1C();
        try {
            total.setId(0L);
            total.setDocDate(DateUtils.getDateTimeNow("yyyy-MM-dd"));
            total.setCashStart(processor.getDoubleAttribute("cash_start"));
            total.setCashEnd(processor.getDoubleAttribute("cash_end"));
            total.setSummaSale(processor.getDoubleAttribute("summa_sale"));
            total.setSummaReturn(processor.getDoubleAttribute("summa_return"));
            total.setSaleCash(processor.getDoubleAttribute("sale_cash"));
            total.setSaleCard(processor.getDoubleAttribute("sale_card"));
            total.setMoneyIn(processor.getDoubleAttribute("money_in"));
            total.setMoneyOut(processor.getDoubleAttribute("money_out"));

        } catch (XMLStreamException ex) {
            Logger.getLogger(TotalsXmlBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }
}
