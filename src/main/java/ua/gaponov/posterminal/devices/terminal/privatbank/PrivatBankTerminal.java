package ua.gaponov.posterminal.devices.terminal.privatbank;

import ua.gaponov.posterminal.devices.terminal.Terminal;
import ua.gaponov.posterminal.documents.orders.Order;

/**
 * @author Andriy Gaponov
 */
public class PrivatBankTerminal implements Terminal {
    @Override
    public boolean pay(int merchId, double summa, Order order) {
        return false;
    }
}
