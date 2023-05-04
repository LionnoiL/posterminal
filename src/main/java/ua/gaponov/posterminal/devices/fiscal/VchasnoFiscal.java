package ua.gaponov.posterminal.devices.fiscal;

/**
 * @author Andriy Gaponov
 */
public class VchasnoFiscal implements DeviceFiscalPrinter {
    @Override
    public void beginReceipt() {

    }

    @Override
    public void endReceipt() {

    }

    @Override
    public void printLine(String sProductName, double dPrice, double dUnits, int taxInfo) {

    }

    @Override
    public void printMessage(String sMessage) {

    }

    @Override
    public void printTotal(String sPayment, double dPaid) {

    }

    @Override
    public void printZReport() {

    }

    @Override
    public void printXReport() {

    }
}
