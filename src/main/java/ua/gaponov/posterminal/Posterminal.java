package ua.gaponov.posterminal;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import ua.gaponov.posterminal.dataexchange.ExchangeScheduler;
import ua.gaponov.posterminal.users.LoginForm;
import ua.gaponov.posterminal.utils.PropertiesUtils;

/**
 *
 * @author wmcon
 */
public class Posterminal {


    public static void main(String[] args) {
        //setTheme();
        PropertiesUtils.loadProperties();
        
        LoginForm.main(null);
        
        AppProperties.scheduler.setTimeReceived();
    }
    
    private static void setTheme(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void closeApp(){
        AppProperties.scheduler.getExchangeTimer().cancel();
    }
}
