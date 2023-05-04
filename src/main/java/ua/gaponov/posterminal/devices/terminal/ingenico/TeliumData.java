package ua.gaponov.posterminal.devices.terminal.ingenico;

import java.io.UnsupportedEncodingException;

import static ua.gaponov.posterminal.devices.terminal.ingenico.Constants.CONTROL_NAMES;
import static ua.gaponov.posterminal.devices.terminal.ingenico.Constants.TERMINAL_DATA_ENCODING;

/**
 * @author Andriy Gaponov
 */
public class TeliumData {

    private String posNumber;
    private double amount;//min 1.0, max 99999.99
    private String paymentMode;
    private String currencyNumeric;
    private String reserved;

    public TeliumData(String posNumber, double amount, String paymentMode, String currencyNumeric, String reserved) {
        try {
            Integer.parseInt(currencyNumeric);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Illegal currencyNumber value");
        }

        if (amount < 1 || amount >= 100000) {
            throw new IllegalArgumentException("Illegal amount value");
        }

        this.posNumber = posNumber;
        this.amount = amount;
        this.paymentMode = paymentMode;
        this.currencyNumeric = currencyNumeric;
        this.reserved = reserved;
    }

    public static String framing(String packet) {
        return (char) CONTROL_NAMES.indexOf("STX") + packet + (char) CONTROL_NAMES.indexOf("ETX") +
                (char) calculateLRC(packet);
    }

    public static byte calculateLRC(String data) {
        byte LRC = 0;
        byte[] bytes;
        try {
            bytes = data.getBytes(TERMINAL_DATA_ENCODING);
            for (int i = 0; i < bytes.length; i++) {
                LRC ^= bytes[i];
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return LRC;
    }

    public String getPosNumber() {
        return posNumber;
    }

    public void setPosNumber(String posNumber) {
        this.posNumber = posNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getCurrencyNumeric() {
        return currencyNumeric;
    }

    public void setCurrencyNumeric(String currencyNumeric) {
        this.currencyNumeric = currencyNumeric;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
}
