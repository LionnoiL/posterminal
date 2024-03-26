package ua.gaponov.posterminal.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.server.commands.PropertyCommand;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;

import static ua.gaponov.posterminal.utils.FilesUtils.getFileInputStream;
import static ua.gaponov.posterminal.utils.JsonUtils.GSON;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertiesUtils {

    public static final String CONFIG_FILE_NAME = "config/application.properties";
    public static final String TEMP_FILE_NAME = "tmp/application.tmp";
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtils.class);

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
        return getPropertyFromFile(TEMP_FILE_NAME, propertyName, "Error read temp file");
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
            LOG.error("Error read temp file");
        }
    }

    public static String getApplicationProperties(String propertyName, boolean isIntegerValue) {
        String propertyValue = getPropertyFromFile(CONFIG_FILE_NAME, propertyName, "Error read properties file");
        if (propertyValue.isEmpty() && isIntegerValue) {
            propertyValue = "0";
        }
        return propertyValue;
    }

    public static String getApplicationProperties(String propertyName) {
        return getPropertyFromFile(CONFIG_FILE_NAME, propertyName, "Error read properties file");
    }

    private static String getPropertyFromFile(String configFileName, String propertyName, String Error_read_properties_file) {
        String propertyValue = "";
        try (FileInputStream fileInputStream = getFileInputStream(configFileName)) {
            propertyValue = getPropertyValue(fileInputStream, propertyName);
        } catch (IOException e) {
            LOG.error(Error_read_properties_file);
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
            LOG.error("Error save properties file");
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
        saveApplicationProperties("shop_id", String.valueOf(AppProperties.getArmId()));
        saveApplicationProperties("shop_guid", AppProperties.getShopGuid());
        saveApplicationProperties("terminal_port", AppProperties.getTerminalPort());
        saveApplicationProperties("cash_register_name", AppProperties.getCashRegisterName());
        saveApplicationProperties("display_port", AppProperties.getDisplayPort());
        saveApplicationProperties("fiscal.name", AppProperties.getFiscalName());
        saveApplicationProperties("fiscal.token", AppProperties.getFiscalToken());
        saveApplicationProperties("fiscal.ip", AppProperties.getFiscalIp());
        saveApplicationProperties("fiscal.auto_plus_sum", String.valueOf(AppProperties.getFiscalAutoPlusMoneySum()));
        saveApplicationProperties("prostopay.token", AppProperties.getProstoPayToken());
        saveApplicationProperties("default_merchant_id", String.valueOf(AppProperties.getDefaultMerchantId()));
        saveApplicationProperties("exchange.http.enable", String.valueOf(AppProperties.isSendDocsOnHttpAfterApprove()));
        saveApplicationProperties("exchange.http.user", AppProperties.getHttpServerLogin());
        saveApplicationProperties("exchange.http.password", AppProperties.getHttpServerPassword());
        saveApplicationProperties("exchange.http.address", AppProperties.getHttpServerIp());
    }

    public static void loadProperties() {
        AppProperties.setServerIpAddress(getApplicationProperties("server_ip"));
        AppProperties.setExchangeInterval(Integer.parseInt(getApplicationProperties("exchange.interval.min", true)) * 60 * 1000);
        AppProperties.setExchangeEnable(Boolean.parseBoolean(getApplicationProperties("exchange.enable")));
        AppProperties.setExchangeFolder(getApplicationProperties("exchange.folder"));
        AppProperties.setWeightItemPrefix(getApplicationProperties("weight_item_prefix"));
        AppProperties.setCurrency(getApplicationProperties("currency"));
        AppProperties.setShopName(getApplicationProperties("shop_name"));
        AppProperties.setShopAddress(getApplicationProperties("shop_address"));
        AppProperties.setArmId(Integer.parseInt(getApplicationProperties("shop_id", true)));
        AppProperties.setShopGuid(getApplicationProperties("shop_guid"));
        AppProperties.setTerminalPort(getApplicationProperties("terminal_port"));
        AppProperties.setCashRegisterName(getApplicationProperties("cash_register_name"));
        AppProperties.setDisplayPort(getApplicationProperties("display_port"));
        AppProperties.setFiscalName(getApplicationProperties("fiscal.name"));
        AppProperties.setFiscalToken(getApplicationProperties("fiscal.token"));
        AppProperties.setFiscalIp(getApplicationProperties("fiscal.ip"));
        AppProperties.setFiscalAutoPlusMoneySum(Double.parseDouble(getApplicationProperties("fiscal.auto_plus_sum")));
        AppProperties.setProstoPayToken(getApplicationProperties("prostopay.token"));
        AppProperties.setDefaultMerchantId(Integer.parseInt(getApplicationProperties("default_merchant_id", true)));
        AppProperties.setSendDocsOnHttpAfterApprove(Boolean.parseBoolean(getApplicationProperties("exchange.http.enable")));
        AppProperties.setHttpServerLogin(getApplicationProperties("exchange.http.user"));
        AppProperties.setHttpServerPassword(getApplicationProperties("exchange.http.password"));
        AppProperties.setHttpServerIp(getApplicationProperties("exchange.http.address"));
    }

    public static PropertyCommand getPropertiesValues() {
        PropertyCommand command = new PropertyCommand();
        command.setShopName(AppProperties.getShopName());
        command.setShopAddress(AppProperties.getShopAddress());
        command.setCashRegisterName(AppProperties.getCashRegisterName());
        command.setFiscalName(AppProperties.getFiscalName());
        command.setFiscalIp(AppProperties.getFiscalIp());
        command.setFiscalToken(AppProperties.getFiscalToken());
        command.setFiscalAutoPlusSum(AppProperties.getFiscalAutoPlusMoneySum());
        command.setProstopayToken(AppProperties.getProstoPayToken());
        command.setTerminalId(AppProperties.getArmId());
        command.setExchangeEnable(AppProperties.isExchangeEnable());
        command.setExchangeIntervalMin(AppProperties.getExchangeInterval() / 60000);
        command.setDefaultMerchantId(AppProperties.getDefaultMerchantId());
        command.setHttpServerIp(AppProperties.getHttpServerIp());
        command.setHttpServerLogin(AppProperties.getHttpServerLogin());
        command.setHttpServerPassword(AppProperties.getHttpServerPassword());
        command.setSendDocsOnHttpAfterApprove(AppProperties.isSendDocsOnHttpAfterApprove());

        return command;
    }

    public static void setPropertiesValues(String requestString) {
        PropertyCommand command = GSON.fromJson(requestString, PropertyCommand.class);
        AppProperties.setShopName(command.getShopName());
        AppProperties.setShopAddress(command.getShopAddress());
        AppProperties.setCashRegisterName(command.getCashRegisterName());
        AppProperties.setFiscalName(command.getFiscalName());
        AppProperties.setFiscalIp(command.getFiscalIp());
        AppProperties.setFiscalToken(command.getFiscalToken());
        AppProperties.setFiscalAutoPlusMoneySum(command.getFiscalAutoPlusSum());
        AppProperties.setProstoPayToken(command.getProstopayToken());
        AppProperties.setExchangeEnable(command.isExchangeEnable());
        AppProperties.setExchangeInterval(command.getExchangeIntervalMin() * 60000);
        AppProperties.setDefaultMerchantId(command.getDefaultMerchantId());
        AppProperties.setHttpServerIp(command.getHttpServerIp());
        AppProperties.setHttpServerLogin(command.getHttpServerLogin());
        if (command.getHttpServerPassword() != null) {
            AppProperties.setHttpServerPassword(command.getHttpServerPassword());
        }
        AppProperties.setSendDocsOnHttpAfterApprove(command.isSendDocsOnHttpAfterApprove());
        saveAllApplicationProperties();
    }
}