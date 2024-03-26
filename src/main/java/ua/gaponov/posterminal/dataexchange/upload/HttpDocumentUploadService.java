package ua.gaponov.posterminal.dataexchange.upload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.entity.messages.ExchangeMessage;
import ua.gaponov.posterminal.entity.messages.ExchangeMessageService;
import ua.gaponov.posterminal.entity.moneymove.MoneyMove;
import ua.gaponov.posterminal.entity.orders.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpDocumentUploadService {

    private static final Logger LOG = LoggerFactory.getLogger(HttpDocumentUploadService.class);

    private static String getBasicAuthString() {
        String login = AppProperties.getHttpServerLogin() + ":" + AppProperties.getHttpServerPassword();
        return "Basic " + Base64.getEncoder().encodeToString(login.getBytes());
    }

    public static void sendOrder(Order order) {
        if (AppProperties.isSendDocsOnHttpAfterApprove()) {
            CompletableFuture.runAsync(() -> {
                try {
                    sendDoc(order.clone());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public static void sendMoneyMove(MoneyMove moneyMoveDoc) {
        if (AppProperties.isSendDocsOnHttpAfterApprove()) {
            CompletableFuture.runAsync(() -> {
                try {
                    sendDoc(moneyMoveDoc.clone());
                } catch (CloneNotSupportedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private static void sendDoc(Object doc) {
        Order order;
        MoneyMove moneyMove;
        List<Order> orders = new ArrayList<>();
        List<MoneyMove> moneyMoves = new ArrayList<>();

        if (doc instanceof Order) {
            order = (Order) doc;
            order.setDate(LocalDateTime.now());
            orders.add(order);
        }

        if (doc instanceof MoneyMove) {
            moneyMove = (MoneyMove) doc;
            moneyMove.setDate(LocalDateTime.now());
            moneyMoves.add(moneyMove);
        }

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
            Connection.Response response = Jsoup.connect(AppProperties.getHttpServerIp() + "?id=" + AppProperties.getArmId())
                    .header("Authorization", getBasicAuthString())
                    .timeout(30000)
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    .requestBody(employeeXml)
                    .execute();
        } catch (Exception e) {
            LOG.error("Http send filed", e);
        }
    }
}
