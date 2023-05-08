package ua.gaponov.posterminal.devices.fiscal;

import ua.gaponov.posterminal.documents.orders.Order;

/**
 * @author Andriy Gaponov
 */
public interface DeviceFiscalPrinter {
    boolean printOrder(Order order);

    boolean printZReport();

    void printXReport();

    boolean shiftIsOpen();

    boolean openShift();

    double getSafeMoney();

    boolean moneyPlus(double money);

    boolean moneyMinus(double money);
}
