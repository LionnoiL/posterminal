package ua.gaponov.posterminal.conf;

import lombok.Getter;
import lombok.Setter;
import ua.gaponov.posterminal.autosaveorder.OrderSaveScheduler;
import ua.gaponov.posterminal.dataexchange.ExchangeScheduler;
import ua.gaponov.posterminal.entity.products.Product;
import ua.gaponov.posterminal.entity.users.User;
import ua.gaponov.posterminal.server.PosHttpServer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andriy Gaponov
 */

public class AppProperties {
    @Getter
    @Setter
    public static boolean exchangeRunning;
    @Getter
    @Setter
    private static int exchangeInterval = 120000;
    @Getter
    @Setter
    private static boolean exchangeEnable = true;
    @Getter
    @Setter
    private static boolean autoSaveEnable = true;
    @Getter
    @Setter
    private static String serverIpAddress;
    @Getter
    @Setter
    private static String weightItemPrefix = "29";
    @Getter
    @Setter
    private static String currency = "грн";
    @Getter
    @Setter
    private static String shopName = "Shop";
    @Getter
    @Setter
    private static String shopAddress = "";
    @Getter
    @Setter
    private static String cashRegisterName = "";
    @Getter
    @Setter
    private static int shopId;
    @Getter
    @Setter
    private static String shopGuid;
    @Getter
    @Setter
    private static ExchangeScheduler scheduler = new ExchangeScheduler();
    @Getter
    @Setter
    private static OrderSaveScheduler autoSaveScheduler = new OrderSaveScheduler();
    @Getter
    @Setter
    private static PosHttpServer httpServer;
    @Getter
    @Setter
    private static User currentUser;
    @Getter
    @Setter
    private static String terminalPort;
    @Getter
    @Setter
    private static String displayPort;
    @Getter
    @Setter
    private static Map<Product, Integer> prostoPayProducts = new HashMap<>();
    @Getter
    @Setter
    private static String fiscalName;
    @Getter
    @Setter
    private static String fiscalToken;
    @Getter
    @Setter
    private static String fiscalIp;
    @Getter
    @Setter
    private static double fiscalAutoPlusMoneySum;
    @Getter
    @Setter
    private static String prostoPayToken;
    @Setter
    private static String exchangeFolder;

    public static String getExchangeFolder() {
        if (!exchangeFolder.isEmpty()){
            exchangeFolder = exchangeFolder.replace("\\", File.separator);
            if (!exchangeFolder.endsWith(File.separator)){
                exchangeFolder += File.separator;
            }
            return exchangeFolder;
        } else {
            return "files"+File.separator;
        }
    }

    private AppProperties() {
    }
}
