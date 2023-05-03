package ua.gaponov.posterminal.devices.fiscal;

public interface DeviceFiscalPrinter {
    void beginReceipt();
    void endReceipt();
    void printLine(String sProductName, double dPrice, double dUnits, int taxInfo);
    void printMessage(String sMessage);
    void printTotal(String sPayment, double dPaid);
    void printZReport();
    void printXReport();
}
