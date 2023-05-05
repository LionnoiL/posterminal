package ua.gaponov.posterminal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.conf.LoggingConfiguration;
import ua.gaponov.posterminal.devices.fiscal.DeviceFiscalPrinter;
import ua.gaponov.posterminal.devices.fiscal.vchasno.VchasnoFiscal;
import ua.gaponov.posterminal.documents.orders.OrderService;
import ua.gaponov.posterminal.forms.login.LoginForm;
import ua.gaponov.posterminal.prostopay.ProstoPayService;
import ua.gaponov.posterminal.utils.PropertiesUtils;

import javax.swing.*;

/**
 * @author Andriy Gaponov
 */
public class PosTerminal {

    private static final Logger LOG = LoggerFactory.getLogger(PosTerminal.class);

    public static void main(String[] args) {

        new LoggingConfiguration();

        LOG.info("Start application");

        setTheme();
        PropertiesUtils.loadProperties();
        ProstoPayService.loadProstoPayProducts();

        DeviceFiscalPrinter fiscal = new VchasnoFiscal("test 1", "B5Rq82gGE6zicywYi6Kcr7NX9XMTlOag");
        fiscal.printOrder(OrderService.getByGuid("ec2b65a8-b2b8-4100-87c3-dcdb66bc4b3d"));

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

        LOG.info("End application");
    }
}
