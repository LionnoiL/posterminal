package ua.gaponov.posterminal.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import ua.gaponov.posterminal.AppProperties;

/**
 * @author wmcon
 */
public class PropertiesUtils {

    private PropertiesUtils(){
    }
    public static final String CONFIG_FILE_NAME = "config/application.properties";
    public static final String TEMP_FILE_NAME = "tmp/application.tmp";

    private static FileInputStream getFileInputStream(String fileName)
        throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()) {
            FilesUtils.checkFileDirAndCreateDir(fileName);
            FilesUtils.saveTextFile(fileName, "");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        return fileInputStream;
    }

    private static String getPropertyValue(FileInputStream fileInputStream, String propertyName)
        throws IOException {
        Properties properties = new Properties();
        properties.load(fileInputStream);
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

    public static void saveApplicationProperties(String propertyName, String propertyValue) {
        try (FileInputStream fileInputStream = getFileInputStream(CONFIG_FILE_NAME)) {
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


    public static void loadProperties() {
        AppProperties.serverIpAdress = getApplicationProperties("server_ip");
        AppProperties.exchangeInterval =
            Integer.parseInt(getApplicationProperties("exchange_interval_min")) * 60 * 1000;
        AppProperties.exchangeEnable = Boolean.parseBoolean(
            getApplicationProperties("exchange_enable"));
        AppProperties.weightItemPrefix = getApplicationProperties("weight_item_prefix");
        AppProperties.currency = getApplicationProperties("currency");
    }
}
