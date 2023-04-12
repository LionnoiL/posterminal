package ua.gaponov.posterminal.dataexchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.util.List;
import ua.gaponov.posterminal.documents.orders.Order;
import ua.gaponov.posterminal.documents.orders.OrderService;

/**
 *
 * @author wmcon
 */
public class ExchangeUpload {
    
    public static void upload(){
        uploadOrders();
    }
    
    private static void uploadOrders(){
        List<Order> items = OrderService.getAllNoUpload();
        OrdersUpload list = new OrdersUpload();
        list.setItems(items);
        
        XmlMapper xmlMapper = new XmlMapper();
        try {
            String employeeXml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
            //TODO save to file if no empty;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } 
    }
}
