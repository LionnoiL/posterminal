package ua.gaponov.posterminal;

import ua.gaponov.posterminal.conf.LoggingConfiguration;
import ua.gaponov.posterminal.forms.login.LoginForm;
import ua.gaponov.posterminal.prostopay.ProstoPayService;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.utils.PropertiesUtils;

import javax.swing.*;

/**
 * @author Andriy Gaponov
 */
public class PosTerminal {


    public static void main(String[] args) {

        new LoggingConfiguration();

        setTheme();
        PropertiesUtils.loadProperties();
        ProstoPayService.loadProstoPayProducts();

        LoginForm.main(null);

        AppProperties.scheduler.setTimeReceived();
    }

    private static void setTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeApp() {
        AppProperties.scheduler.getExchangeTimer().cancel();
        AppProperties.autoSaveScheduler.getTimer().cancel();
    }
}
