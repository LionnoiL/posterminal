package ua.gaponov.posterminal.devices.fiscal;

/**
 * @author Andriy Gaponov
 */
public interface DeviceFiscalPrinter {
    void beginReceipt();

    boolean endReceipt();

    void printLine(String sProductName, double dPrice, double dUnits, int taxInfo);

    void printMessage(String sMessage);

    void printTotal(String sPayment, double dPaid);

    boolean printZReport();

    void printXReport();

    boolean shiftIsOpen();
    boolean openShift();
}
