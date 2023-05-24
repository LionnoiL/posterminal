package ua.gaponov.posterminal.utils;

import ua.gaponov.posterminal.conf.AppProperties;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

import static ua.gaponov.posterminal.utils.FilesUtils.getFileInputStream;

/**
 * @author Andriy Gaponov
 */
public class PropertiesUtils {

    public static final String CONFIG_FILE_NAME = "config/application.properties";
    public static final String TEMP_FILE_NAME = "tmp/application.tmp";
    private PropertiesUtils() {
    }



    private static String getPropertyValue(FileInputStream fileInputStream, String propertyName)
            throws IOException {
        Properties properties = new Properties();
        properties.load(fileInputStream);
        if (!properties.containsKey(propertyName)) {
            properties.put(propertyName, "");
        }
        return properties.getProperty(propertyName);
    }

    public static String getApplicationTempValue(String propertyName) {
        String propertyValue = "";
        try (FileInputStream fileInputStream = getFileInputStream(TEMP_FILE_NAME)) {
            propertyValue = getPropertyValue(fileInputStream, propertyName);
        } catch (IOException e) {
            System.out.println("Error read temp file");
        }
        return propertyValue;
    }

    public static void saveApplicationTempValue(String propertyName, String propertyValue) {
        try (FileInputStream fileInputStream = getFileInputStream(TEMP_FILE_NAME)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            properties.setProperty(propertyName, propertyValue);
            try (OutputStream output = new FileOutputStream(TEMP_FILE_NAME)) {
                properties.store(output, null);
            } catch (IOException io) {
                io.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Error read properties file");
        }
    }

    public static String getApplicationProperties(String propertyName) {
        String propertyValue = "";
        try (FileInputStream fileInputStream = getFileInputStream(CONFIG_FILE_NAME)) {
            propertyValue = getPropertyValue(fileInputStream, propertyName);
        } catch (IOException e) {
            System.out.println("Error read properties file");
        }
        return propertyValue;
    }

    private static void saveApplicationProperties(String propertyName, String propertyValue) {
        try (FileInputStream fileInputStream = getFileInputStream(CONFIG_FILE_NAME)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            if (Objects.isNull(propertyValue)) {
                propertyValue = "";
            }
            properties.setProperty(propertyName, propertyValue);

            try (OutputStream output = new FileOutputStream(CONFIG_FILE_NAME)) {
                properties.store(output, null);
            } catch (IOException io) {
                io.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Error read properties file");
        }
    }

    public static void saveAllApplicationProperties() {
        saveApplicationProperties("currency", AppProperties.getCurrency());
        saveApplicationProperties("weight_item_prefix", AppProperties.getWeightItemPrefix());
        saveApplicationProperties("exchange.enable", String.valueOf(AppProperties.isExchangeEnable()));
        saveApplicationProperties("exchange.interval.min", String.valueOf(AppProperties.getExchangeInterval() / 60000));
        saveApplicationProperties("exchange.folder", AppProperties.getExchangeFolder());
        saveApplicationProperties("shop_name", AppProperties.getShopName());
        saveApplicationProperties("shop_address", AppProperties.getShopAddress());
        saveApplicationProperties("shop_id", String.valueOf(AppProperties.getShopId()));
        saveApplicationProperties("shop_guid", AppProperties.getShopGuid());
        saveApplicationProperties("terminal_port", AppProperties.getTerminalPort());
        saveApplicationProperties("cash_register_name", AppProperties.getCashRegisterName());
        saveApplicationProperties("display_port", AppProperties.getDisplayPort());
        saveApplicationProperties("fiscal.name", AppProperties.getFiscalName());
        saveApplicationProperties("fiscal.token", AppProperties.getFiscalToken());
        saveApplicationProperties("fiscal.ip", AppProperties.getFiscalIp());
        saveApplicationProperties("fiscal.auto_plus_sum", String.valueOf(AppProperties.getFiscalAutoPlusMoneySum()));
        saveApplicationProperties("prostopay.token", AppProperties.getProstoPayToken());
    }

    public static void loadProperties() {
        AppProperties.setServerIpAddress(getApplicationProperties("server_ip"));
        AppProperties.setExchangeInterval(Integer.parseInt(getApplicationProperties("exchange.interval.min")) * 60 * 1000);
        AppProperties.setExchangeEnable(Boolean.parseBoolean(getApplicationProperties("exchange.enable")));
        AppProperties.setExchangeFolder(getApplicationProperties("exchange.folder"));
        AppProperties.setWeightItemPrefix(getApplicationProperties("weight_item_prefix"));
        AppProperties.setCurrency(getApplicationProperties("currency"));
        AppProperties.setShopName(getApplicationProperties("shop_name"));
        AppProperties.setShopAddress(getApplicationProperties("shop_address"));
        AppProperties.setShopId(Integer.parseInt(getApplicationProperties("shop_id")));
        AppProperties.setShopGuid(getApplicationProperties("shop_guid"));
        AppProperties.setTerminalPort(getApplicationProperties("terminal_port"));
        AppProperties.setCashRegisterName(getApplicationProperties("cash_register_name"));
        AppProperties.setDisplayPort(getApplicationProperties("display_port"));
        AppProperties.setFiscalName(getApplicationProperties("fiscal.name"));
        AppProperties.setFiscalToken(getApplicationProperties("fiscal.token"));
        AppProperties.setFiscalIp(getApplicationProperties("fiscal.ip"));
        AppProperties.setFiscalAutoPlusMoneySum(Double.parseDouble(getApplicationProperties("fiscal.auto_plus_sum")));
        AppProperties.setProstoPayToken(getApplicationProperties("prostopay.token"));
    }
}
