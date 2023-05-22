package ua.gaponov.posterminal.dataexchange;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;
import ua.gaponov.posterminal.entity.messages.ExchangeMessage;
import ua.gaponov.posterminal.entity.orders.Order;

import java.util.List;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
@JacksonXmlRootElement(localName = "data")
public class OrdersUpload {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "order")
    private List<Order> items;
    private String shopGuid;
    private int shopId;
    private ExchangeMessage exchangeMessage;
}
