package ua.gaponov.posterminal.dataexchange;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import ua.gaponov.posterminal.AppProperties;

/**
 *
 * @author wmcon
 */
public class ExchangeScheduler {
    
    public Timer exchangeTimer = new Timer();
    
    public void setTimeReceived() {

        Calendar calendar = Calendar.getInstance();
        
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (AppProperties.exchangeEnable){
                    ExchangeUpload.upload();
                    //TODO exchange
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
