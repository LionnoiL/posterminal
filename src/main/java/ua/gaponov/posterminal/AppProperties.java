package ua.gaponov.posterminal;

import ua.gaponov.posterminal.dataexchange.ExchangeScheduler;

/**
 *
 * @author wmcon
 */
public class AppProperties {
    public static String lastUserName;
    public static int exchangeInterval = 120000;
    public static boolean exchangeEnable = true;
    
    public static String serverIpAdress;
    
    public static ExchangeScheduler scheduler = new ExchangeScheduler();
}
