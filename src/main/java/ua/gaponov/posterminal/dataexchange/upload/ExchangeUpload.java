package ua.gaponov.posterminal.dataexchange.upload;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.dataexchange.ExchangeBuilder;
import ua.gaponov.posterminal.dataexchange.download.ExchangeDownloader;
import ua.gaponov.posterminal.dataexchange.download.UpdateDownloadException;
import ua.gaponov.posterminal.entity.confirmation.Confirmation;
import ua.gaponov.posterminal.entity.confirmation.ConfirmationService;
import ua.gaponov.posterminal.entity.confirmation.ConfirmationXmlBuilder;
import ua.gaponov.posterminal.entity.messages.ExchangeMessage;
import ua.gaponov.posterminal.entity.messages.ExchangeMessageService;
import ua.gaponov.posterminal.entity.moneymove.MoneyMove;
import ua.gaponov.posterminal.entity.moneymove.MoneyMoveService;
import ua.gaponov.posterminal.entity.orders.Order;
import ua.gaponov.posterminal.entity.orders.OrderService;
import ua.gaponov.posterminal.utils.FilesUtils;
import ua.gaponov.posterminal.utils.XmlUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

import static ua.gaponov.posterminal.conf.LoggingConfiguration.LOG_FILE;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeUpload {
    public static final String IMPORT_FILE_CONFIRMATION = AppProperties.getExchangeFolder() + "confirmation_" + AppProperties.getArmId() + ".xml";
    public static final String EXPORT_FILE = AppProperties.getExchangeFolder() + "export_" + AppProperties.getArmId() + ".xml";
    public static final String EXPORT_LOCAL_FILE = "files/export_" + AppProperties.getArmId() + ".xml";
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeUpload.class);
    private static final ExchangeBuilder<Confirmation, XmlUtils> CONFIRMATION_BUILDER = new ConfirmationXmlBuilder();

    private static final String DEFAULT_FILE_NAME = "config/application.properties";

    private static final Properties PROPERTIES = new Properties();

    public static void uploadDownload() {
        ExchangeUpload.upload();
        try {
            ExchangeDownloader.download();
        } catch (UpdateDownloadException ex) {
            LOG.error("Import filed", ex);
        }
    }

    public static void upload() {
        downloadConfirmations();
        uploadOrders();
    }

    private static void uploadOrders() {
        List<Order> orders = OrderService.getAllNoUpload();
        List<MoneyMove> moneyMoves = MoneyMoveService.getAllNoUpload();

        ExchangeMessage messages = ExchangeMessageService.getMessages();
        messages.setReceivedNumber(messages.getReceivedNumber() + 1);

        DocumentsUpload list = new DocumentsUpload();
        list.setExchangeMessage(messages);
        list.setShopGuid(AppProperties.getShopGuid());
        list.setArmId(AppProperties.getArmId());

        list.setOrders(orders);
        list.setMoneyMoves(moneyMoves);

        ObjectMapper xmlMapper = new XmlMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        xmlMapper.registerModule(module);

        xmlMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
        try {
            String employeeXml = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(list);
            FilesUtils.saveTextFile(EXPORT_LOCAL_FILE, employeeXml);
            FilesUtils.saveTextFile(EXPORT_FILE, employeeXml);
            ExchangeMessageService.saveMessages(messages);
        } catch (JsonProcessingException | SQLException e) {
            LOG.error("Export filed", e);
        }

        orders = null;
        moneyMoves = null;
    }

    private static void downloadConfirmations() {
        if (!FilesUtils.fileExist(IMPORT_FILE_CONFIRMATION)) {
            return;
        }

        LOG.info("Start import confirmations");
        Path path = Paths.get(IMPORT_FILE_CONFIRMATION);
        try (InputStream is = Files.newInputStream(path);
             XmlUtils processor = new XmlUtils(is)) {
            while (processor.startElement("confirmation", "confirmations")) {
                Confirmation confirmation = CONFIRMATION_BUILDER.create(processor);
                ConfirmationService.save(confirmation);
                confirmation = null;
            }
        } catch (Exception e) {
            LOG.error("Import confirmations filed", e);
        }

        try {
            FilesUtils.deleteFile(IMPORT_FILE_CONFIRMATION);
        } catch (IOException e) {
            LOG.error("Delete confirmation file failed");
        }

        LOG.info("End import confirmations");
    }

    public static void uploadLog() {
        try {
            PROPERTIES.load(FilesUtils.getFileInputStream(DEFAULT_FILE_NAME));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String logFilePath = PROPERTIES.getProperty(LOG_FILE);
        String copyDestinationPath = AppProperties.getExchangeFolder() + "log_" + AppProperties.getArmId() + ".xml";

        try (InputStream in = new FileInputStream(logFilePath);
             OutputStream out = new FileOutputStream(copyDestinationPath)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            System.out.println("Log file copied successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
