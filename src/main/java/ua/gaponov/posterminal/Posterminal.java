package ua.gaponov.posterminal;

import net.sf.jasperreports.engine.JRException;
import ua.gaponov.posterminal.forms.login.LoginForm;
import ua.gaponov.posterminal.reports.JReport;
import ua.gaponov.posterminal.utils.CommStream;
import ua.gaponov.posterminal.utils.PropertiesUtils;

import javax.swing.*;
import java.io.IOException;

/**
 * @author wmcon
 */
public class Posterminal {


    public static void main(String[] args) {
//        try {
//            JReport.printReport();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (JRException e) {
//            throw new RuntimeException(e);
//        }
        setTheme();
        PropertiesUtils.loadProperties();

        LoginForm.main(null);

        AppProperties.scheduler.setTimeReceived();

        CommStream.listCommPorts();
    }

    private static void setTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            //javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeApp() {
        AppProperties.scheduler.getExchangeTimer().cancel();
    }
}
