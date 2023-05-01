package ua.gaponov.posterminal;

import ua.gaponov.posterminal.forms.login.LoginForm;
import ua.gaponov.posterminal.utils.CommStream;
import ua.gaponov.posterminal.utils.PropertiesUtils;

import javax.swing.*;

/**
 * @author wmcon
 */
public class Posterminal {


    public static void main(String[] args) {
        setTheme();
        PropertiesUtils.loadProperties();

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
