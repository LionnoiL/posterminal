package ua.gaponov.posterminal.autosaveorder;

import lombok.Getter;
import lombok.Setter;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.entity.orders.Order;
import ua.gaponov.posterminal.entity.orders.OrderService;

import java.util.Calendar;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
* @author Andriy Gaponov
*/
@Setter
@Getter
public class OrderSaveScheduler {
    public Timer timer = new Timer();
    private Map<Integer, Order> orders;

    public void setTimeReceived(Map<Integer, Order> orders) {
        this.orders = orders;

        Calendar calendar = Calendar.getInstance();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (AppProperties.isAutoSaveEnable()) {
                    OrderService.saveOrderToBackupDir(getOrders());
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
