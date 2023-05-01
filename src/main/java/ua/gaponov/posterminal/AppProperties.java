package ua.gaponov.posterminal;

import ua.gaponov.posterminal.dataexchange.ExchangeScheduler;
import ua.gaponov.posterminal.users.User;

/**
 *
 * @author wmcon
 */
public class AppProperties {
    private AppProperties(){
    }

    public static int exchangeInterval = 120000;
    public static boolean exchangeEnable = true;
    public static String serverIpAdress;
    public static String weightItemPrefix = "29";
    public static String currency = "грн";

    public static String shopName = "Shop";
    public static String shopAddress = "";
    public static int shopId;
    public static String shopGuid;

    public static ExchangeScheduler scheduler = new ExchangeScheduler();
    public static User currentUser;
    public static boolean exchangeRunning;

    public static String terminalPort;
}
