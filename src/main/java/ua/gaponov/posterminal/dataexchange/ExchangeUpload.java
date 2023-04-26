package ua.gaponov.posterminal.dataexchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.util.List;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ua.gaponov.posterminal.documents.orders.Order;
import ua.gaponov.posterminal.documents.orders.OrderService;
import ua.gaponov.posterminal.utils.FilesUtils;

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
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        xmlMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
        try {
            String employeeXml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
            FilesUtils.saveTextFile("files/export.xml", employeeXml);
            //TODO save to file if no empty;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
