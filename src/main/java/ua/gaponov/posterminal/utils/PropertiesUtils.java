package ua.gaponov.posterminal.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import ua.gaponov.posterminal.AppProperties;

/**
 *
 * @author wmcon
 */
public class PropertiesUtils {
    
    public static final String CONFIG_FILE_NAME = "config/application.properties";

    public static String getApplicationProperties(String propertyName) {
        String propertyValue = "";
        File file = new File(CONFIG_FILE_NAME);
        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                Properties properties = new Properties();
                properties.load(fileInputStream);
                propertyValue = properties.getProperty(propertyName);
            } catch (IOException e) {
                System.out.println("Error read properties file");
            }
        }

        return propertyValue;
    }
    
    public static void saveApplicationProperties(String propertyName, String propertyValue) {
        File file = new File(CONFIG_FILE_NAME);
        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                Properties properties = new Properties();
                properties.load(fileInputStream);
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
    }
            
    
    public static void loadProperties(){
        AppProperties.lastUserName = getApplicationProperties("last_user");
        AppProperties.serverIpAdress = getApplicationProperties("server_ip");
        AppProperties.exchangeInterval = Integer.parseInt(getApplicationProperties("exchange_interval_min"))*60*1000;
        AppProperties.exchangeEnable = Boolean.parseBoolean(getApplicationProperties("exchange_enable"));
        AppProperties.weightItemPrefix = getApplicationProperties("weight_item_prefix");
        AppProperties.currency = getApplicationProperties("currency");
    }
}
