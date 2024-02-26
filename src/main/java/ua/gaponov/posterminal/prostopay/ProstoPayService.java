package ua.gaponov.posterminal.prostopay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.entity.orders.Order;
import ua.gaponov.posterminal.entity.products.Product;
import ua.gaponov.posterminal.entity.products.ProductService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Properties;

import static ua.gaponov.posterminal.utils.FilesUtils.getFileInputStream;
import static ua.gaponov.posterminal.utils.JsonUtils.GSON;

/**
 * @author Andriy Gaponov
 */
public final class ProstoPayService {

    private static final Logger LOG = LoggerFactory.getLogger(ProstoPayService.class);
    private static final String PROSTOPAY_HOST = "https://dashboard.prostopay.net/api/v1/qreceipt/generate";
    private static final String PROSTOPAY_API_KEY = AppProperties.getProstoPayToken();
    private static final String PROSTOPAY_PRODUCTS_FILE_NAME = "config/prostopay-products.properties";

    private ProstoPayService() {
    }

    public static void loadProstoPayProducts() {
        Properties properties = new Properties();
        try {
            properties.load(getFileInputStream(PROSTOPAY_PRODUCTS_FILE_NAME));
            for (Map.Entry<Object, Object> property : properties.entrySet()) {
                Integer key = Integer.parseInt((String) property.getKey());
                String productCode = (String) property.getValue();
                AppProperties.getProstoPayProducts().put(ProductService.getByCode(productCode), key);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printQrCodesByOrder(Order order) {
        order.getDetails().forEach(detail -> {
            if (detail.getProduct().isProstoPayProduct()) {
                int qty = (int) detail.getQty();
                for (int i = 0; i < qty; i++) {
                    ProstoPayRequest prostoPayRequest = getProstoPayRequest(detail.getProduct(), i + 1);
                    String qrCode = getQrCode(prostoPayRequest);
                    if (!qrCode.isEmpty()) {
                        new PrintCoffeeBarCode(detail.getProduct(), qrCode);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
    }

    private static ProstoPayRequest getProstoPayRequest(Product product, int number) {
        ProstoPayRequest prostoPayRequest = new ProstoPayRequest();
        prostoPayRequest.setPos(AppProperties.getArmId());
        prostoPayRequest.setTill(AppProperties.getArmId());
        prostoPayRequest.setNumber(number);
        prostoPayRequest.setAmount((int) (product.getPrice() * 100));
        prostoPayRequest.setPluFrom(AppProperties.getProstoPayProducts().get(product));
        return prostoPayRequest;
    }

    private static String getQrCode(ProstoPayRequest prostoPayRequest) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PROSTOPAY_HOST))
                .header("Content-type", "application/json; charset=UTF-8")
                .header("X-API-KEY", PROSTOPAY_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(prostoPayRequest)))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            }else{
                LOG.error("ProstoPay request failed. Status code: {}", response.statusCode());
                return "";
            }
        } catch (IOException | InterruptedException e) {
            LOG.error("ProstoPay request failed", e);
            return "";
        }
    }
}
