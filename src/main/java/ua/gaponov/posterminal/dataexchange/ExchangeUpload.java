package ua.gaponov.posterminal.dataexchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.entity.orders.Order;
import ua.gaponov.posterminal.entity.orders.OrderService;
import ua.gaponov.posterminal.utils.FilesUtils;

import java.util.List;

/**
 * @author Andriy Gaponov
 */
public class ExchangeUpload {
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeUpload.class);

    public static void upload() {
        uploadOrders();
    }

    private static void uploadOrders() {
        List<Order> items = OrderService.getAllNoUpload();
        if (items.size() > 0) {
            OrdersUpload list = new OrdersUpload();
            list.setShopGuid(AppProperties.shopGuid);
            list.setShopId(AppProperties.shopId);
            list.setItems(items);

            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.registerModule(new JavaTimeModule());
            xmlMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            xmlMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
            try {
                String employeeXml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
                FilesUtils.saveTextFile("files/export_" + AppProperties.shopId + ".xml", employeeXml);
            } catch (JsonProcessingException e) {
                LOG.error("Export filed", e);
            }
        }
    }
}
