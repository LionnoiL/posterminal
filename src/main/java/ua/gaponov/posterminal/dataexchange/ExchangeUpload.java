package ua.gaponov.posterminal.dataexchange;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.entity.messages.ExchangeMessage;
import ua.gaponov.posterminal.entity.messages.ExchangeMessageService;
import ua.gaponov.posterminal.entity.orders.Order;
import ua.gaponov.posterminal.entity.orders.OrderService;
import ua.gaponov.posterminal.utils.FilesUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        ExchangeMessage messages = ExchangeMessageService.getMessages();
        messages.setReceivedNumber(messages.getReceivedNumber() + 1);
        if (items.size() > 0) {
            OrdersUpload list = new OrdersUpload();
            list.setExchangeMessage(messages);
            list.setShopGuid(AppProperties.shopGuid);
            list.setShopId(AppProperties.shopId);
            list.setItems(items);

            ObjectMapper xmlMapper = new XmlMapper();

            SimpleModule module = new SimpleModule();
            module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
            xmlMapper.registerModule(module);

            xmlMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
            try {
                String employeeXml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
                FilesUtils.saveTextFile("files/export_" + AppProperties.shopId + ".xml", employeeXml);
                ExchangeMessageService.saveMessages(messages);
            } catch (JsonProcessingException | SQLException e) {
                LOG.error("Export filed", e);
            }
        }
    }

    private static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(FORMATTER));
        }
    }
}
