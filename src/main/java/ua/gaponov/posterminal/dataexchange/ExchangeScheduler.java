package ua.gaponov.posterminal.dataexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.dataexchange.download.ExchangeDownloader;
import ua.gaponov.posterminal.dataexchange.download.UpdateDownloadException;
import ua.gaponov.posterminal.dataexchange.upload.ExchangeUpload;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Andriy Gaponov
 */
public class ExchangeScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(ExchangeScheduler.class);
    public static final Timer EXCHANGE_TIMER = new Timer();

    public void setTimeReceived() {

        Calendar calendar = Calendar.getInstance();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (AppProperties.isExchangeEnable()) {
                    ExchangeUpload.uploadDownload();
                }
            }
        };

        int exchangeInterval = AppProperties.getExchangeInterval();
        if (exchangeInterval <= 0){
            exchangeInterval = 10 * 60000;
        }
        EXCHANGE_TIMER.schedule(
                timerTask,
                calendar.getTime(),
                exchangeInterval
        );
    }

    public Timer getExchangeTimer() {
        return EXCHANGE_TIMER;
    }
}
