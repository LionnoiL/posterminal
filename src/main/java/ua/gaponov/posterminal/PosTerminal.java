package ua.gaponov.posterminal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.conf.LoggingConfiguration;
import ua.gaponov.posterminal.forms.login.LoginForm;
import ua.gaponov.posterminal.prostopay.ProstoPayService;
import ua.gaponov.posterminal.server.PosHttpServer;
import ua.gaponov.posterminal.utils.PropertiesUtils;

import javax.swing.*;
import java.io.IOException;

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


        LoginForm.main(null);

        AppProperties.getScheduler().setTimeReceived();

        startHttpServer();
    }

    private static void startHttpServer() {
        final int port = 5555;
        final String path = "/";
        final int nThreads = 2;
        try {
            AppProperties.setHttpServer(new PosHttpServer(port, path, nThreads));
            AppProperties.getHttpServer().start();
        } catch (IOException e) {
            LOG.error("PosServer start failed");
        }
    }

    private static void setTheme() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeApp() {
        AppProperties.getScheduler().getExchangeTimer().cancel();
        AppProperties.getAutoSaveScheduler().getTimer().cancel();
        AppProperties.getHttpServer().stop();
        LOG.info("End application");
    }
}
