package ua.gaponov.posterminal.devices.terminal.privatbank;

import ua.gaponov.posterminal.devices.terminal.Terminal;

public class PrivatBankTerminal implements Terminal {
    @Override
    public boolean pay(int merchId, double summa) {
        return false;
    }
}
