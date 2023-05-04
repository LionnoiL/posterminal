package ua.gaponov.posterminal.dataexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Andriy Gaponov
 */
public class ExchangeScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeDownloader.class);
    public Timer exchangeTimer = new Timer();

    public void setTimeReceived() {

        Calendar calendar = Calendar.getInstance();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (AppProperties.exchangeEnable) {
                    ExchangeUpload.upload();
                    try {
                        ExchangeDownloader.download();
                    } catch (Exception ex) {
                        LOG.error("Import filed", ex);
                    }
                }
            }
        };

        exchangeTimer.schedule(
                timerTask,
                calendar.getTime(),
                AppProperties.exchangeInterval
        );
    }

    public Timer getExchangeTimer() {
        return exchangeTimer;
    }
}
