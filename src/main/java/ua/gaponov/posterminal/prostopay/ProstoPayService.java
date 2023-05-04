package ua.gaponov.posterminal.prostopay;

import ua.gaponov.posterminal.devices.printer.PrintCoffeeBarCode;
import ua.gaponov.posterminal.documents.orders.Order;
import ua.gaponov.posterminal.products.Product;
import ua.gaponov.posterminal.products.ProductService;
import ua.gaponov.posterminal.utils.AppProperties;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import static ua.gaponov.posterminal.utils.FilesUtils.getFileInputStream;
import static ua.gaponov.posterminal.utils.JsonUtils.GSON;

/**
 * @author Andriy Gaponov
 */
public class ProstoPayService {

    private static final String PROSTOPAY_HOST = "https://dashboard.prostopay.net/api/v1/qreceipt/generate";
    private static final String PROSTOPAY_API_KEY = "d4666842-60b9-4b47-bb0d-f4ccda6e0a9f";
    private static final String PROSTOPAY_PRODUCTS_FILE_NAME = "config/prostopay-products.properties";

    private ProstoPayService() {
    }

    public static void loadProstoPayProducts(){
        Properties properties = new Properties();
        try {
            properties.load(getFileInputStream(PROSTOPAY_PRODUCTS_FILE_NAME));
            for (Map.Entry<Object, Object> property : properties.entrySet()) {
                Integer key = Integer.parseInt((String) property.getKey());
                String productCode = (String) property.getValue();
                AppProperties.prostoPayProducts.put(ProductService.getByCode(productCode), key);
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
                    ProstoPayRequest prostoPayRequest = getProstoPayRequest(detail.getProduct());
                    String qrCode = getQrCode(prostoPayRequest);
                    if (!qrCode.isEmpty()) {
                        new PrintCoffeeBarCode(detail.getProduct(), qrCode);
                    }
                }
            }
        });
    }

    private static ProstoPayRequest getProstoPayRequest(Product product){
        ProstoPayRequest prostoPayRequest = new ProstoPayRequest();
        prostoPayRequest.setPos(AppProperties.shopId);
        prostoPayRequest.setTill(1);
        prostoPayRequest.setNumber(1);
        prostoPayRequest.setAmount((int) (product.getPrice() * 100));
        prostoPayRequest.setPluFrom(AppProperties.prostoPayProducts.get(product));//product code in coffee machine
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
            }
        } catch (IOException | InterruptedException e) {
            return "";
        }
        return "";
    }
}
