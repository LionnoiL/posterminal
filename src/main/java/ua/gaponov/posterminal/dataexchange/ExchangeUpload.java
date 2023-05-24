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
import ua.gaponov.posterminal.entity.confirmation.Confirmation;
import ua.gaponov.posterminal.entity.confirmation.ConfirmationService;
import ua.gaponov.posterminal.entity.confirmation.ConfirmationXmlBuilder;
import ua.gaponov.posterminal.entity.messages.ExchangeMessage;
import ua.gaponov.posterminal.entity.messages.ExchangeMessageService;
import ua.gaponov.posterminal.entity.moneymove.MoneyMove;
import ua.gaponov.posterminal.entity.moneymove.MoneyMoveService;
import ua.gaponov.posterminal.entity.orders.Order;
import ua.gaponov.posterminal.entity.orders.OrderService;
import ua.gaponov.posterminal.entity.products.Product;
import ua.gaponov.posterminal.entity.products.ProductXmlBuilder;
import ua.gaponov.posterminal.utils.FilesUtils;
import ua.gaponov.posterminal.utils.XmlUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Andriy Gaponov
 */
public class ExchangeUpload {
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeUpload.class);
    public static final String IMPORT_FILE_CONFIRMATION = "files/confirmation_" + AppProperties.getShopId() + ".xml";
    private static final ExchangeBuilder<Confirmation, XmlUtils> confirmationBuilder = new ConfirmationXmlBuilder();

    public static void upload() {
        downloadConfirmations();
        uploadOrders();
    }

    private static void uploadOrders() {
        List<Order> orders = OrderService.getAllNoUpload();
        List<MoneyMove> moneyMoves = MoneyMoveService.getAllNoUpload();

        ExchangeMessage messages = ExchangeMessageService.getMessages();
        messages.setReceivedNumber(messages.getReceivedNumber() + 1);
        if (orders.size() + moneyMoves.size() > 0) {
            DocumentsUpload list = new DocumentsUpload();
            list.setExchangeMessage(messages);
            list.setShopGuid(AppProperties.getShopGuid());
            list.setShopId(AppProperties.getShopId());

            list.setOrders(orders);
            list.setMoneyMoves(moneyMoves);

            ObjectMapper xmlMapper = new XmlMapper();
            SimpleModule module = new SimpleModule();
            module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
            xmlMapper.registerModule(module);

            xmlMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
            try {
                String employeeXml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
                FilesUtils.saveTextFile("files/export_" + AppProperties.getShopId() + ".xml", employeeXml);
                ExchangeMessageService.saveMessages(messages);
            } catch (JsonProcessingException | SQLException e) {
                LOG.error("Export filed", e);
            }
        }
    }

    private static void downloadConfirmations() {
        LOG.info("Start import confirmations");
        try (XmlUtils processor = new XmlUtils(Files.newInputStream(Paths.get(IMPORT_FILE_CONFIRMATION)))) {
            while (processor.startElement("confirmations", "confirmation")) {
                Confirmation confirmation = confirmationBuilder.create(processor);
                ConfirmationService.save(confirmation);
            }
            FilesUtils.deleteFile(IMPORT_FILE_CONFIRMATION);
        } catch (Exception e) {
            LOG.error("Import confirmations filed", e);
        }
        LOG.info("End import confirmations");
    }

    private static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.format(FORMATTER));
        }
    }
}
