package ua.gaponov.posterminal.dataexchange;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;
import ua.gaponov.posterminal.documents.orders.Order;

/**
 *
 * @author wmcon
 */
@JacksonXmlRootElement(localName = "orders")
public class OrdersUpload {
    
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "order")
    private List<Order> items; 

    public List<Order> getItems() {
        return items;
    }

    public void setItems(List<Order> items) {
        this.items = items;
    }
}
