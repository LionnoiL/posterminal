package ua.gaponov.posterminal.autosaveorder;

import lombok.Getter;
import lombok.Setter;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.entity.orders.Order;
import ua.gaponov.posterminal.entity.orders.OrderService;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
* @author Andriy Gaponov
*/
@Setter
@Getter
public class OrderSaveScheduler {
    public Timer timer = new Timer();
    private Order order;

    public void setTimeReceived(Order order) {
        this.order = order;

        Calendar calendar = Calendar.getInstance();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (AppProperties.isAutoSaveEnable()) {
                    OrderService.saveOrderToBackupDir(getOrder());
                }
            }
        };
        timer.schedule(
                timerTask,
                calendar.getTime(),
                3000
        );
    }
}
