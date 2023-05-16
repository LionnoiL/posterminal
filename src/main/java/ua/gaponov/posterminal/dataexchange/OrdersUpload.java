package ua.gaponov.posterminal.dataexchange;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import ua.gaponov.posterminal.entity.orders.Order;

import java.util.List;

/**
 * @author Andriy Gaponov
 */
@JacksonXmlRootElement(localName = "orders")
public class OrdersUpload {

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "order")
    private List<Order> items;

    private String shopGuid;
    private int shopId;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopGuid() {
        return shopGuid;
    }

    public void setShopGuid(String shopGuid) {
        this.shopGuid = shopGuid;
    }

    public List<Order> getItems() {
        return items;
    }

    public void setItems(List<Order> items) {
        this.items = items;
    }
}
