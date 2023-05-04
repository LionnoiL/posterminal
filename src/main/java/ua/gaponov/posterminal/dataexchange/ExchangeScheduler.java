package ua.gaponov.posterminal.dataexchange;

import ua.gaponov.posterminal.utils.AppProperties;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Andriy Gaponov
 */
public class ExchangeScheduler {

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
                        Logger.getLogger(ExchangeScheduler.class.getName()).log(Level.SEVERE, null, ex);
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
