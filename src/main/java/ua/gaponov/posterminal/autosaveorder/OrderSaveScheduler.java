package ua.gaponov.posterminal.autosaveorder;

import ua.gaponov.posterminal.utils.AppProperties;
import ua.gaponov.posterminal.documents.orders.Order;
import ua.gaponov.posterminal.documents.orders.OrderService;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class OrderSaveScheduler {
    public Timer timer = new Timer();
    private Order order;

    public void setTimeReceived(Order order) {
        this.order = order;

        Calendar calendar = Calendar.getInstance();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (AppProperties.autoSaveEnable) {
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

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public Timer getTimer() {
        return timer;
    }
}
