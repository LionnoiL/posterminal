package ua.gaponov.posterminal.conf;

import ua.gaponov.posterminal.autosaveorder.OrderSaveScheduler;
import ua.gaponov.posterminal.dataexchange.ExchangeScheduler;
import ua.gaponov.posterminal.products.Product;
import ua.gaponov.posterminal.users.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andriy Gaponov
 */
public class AppProperties {
    public static int exchangeInterval = 120000;
    public static boolean exchangeEnable = true;
    public static boolean autoSaveEnable = true;
    public static String serverIpAdress;
    public static String weightItemPrefix = "29";
    public static String currency = "грн";
    public static String shopName = "Shop";
    public static String shopAddress = "";
    public static String cashRegisterName = "";
    public static int shopId;
    public static String shopGuid;
    public static ExchangeScheduler scheduler = new ExchangeScheduler();
    public static OrderSaveScheduler autoSaveScheduler = new OrderSaveScheduler();
    public static User currentUser;
    public static boolean exchangeRunning;
    public static String terminalPort;
    public static String displayPort;
    public static Map<Product, Integer> prostoPayProducts = new HashMap<>();
    private AppProperties() {
    }
}
