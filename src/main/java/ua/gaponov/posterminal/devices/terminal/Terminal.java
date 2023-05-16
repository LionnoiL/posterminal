package ua.gaponov.posterminal.devices.terminal;

import ua.gaponov.posterminal.entity.orders.Order;

/**
 * @author Andriy Gaponov
 */
public interface Terminal {
    boolean pay(int merchId, double summa, Order order);
}
