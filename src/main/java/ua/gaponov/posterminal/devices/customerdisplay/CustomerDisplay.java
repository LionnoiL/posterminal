package ua.gaponov.posterminal.devices.customerdisplay;

/**
 * @author Andriy Gaponov
 */
public interface CustomerDisplay {
    void writeDisplay(String sLine1, String sLine2);

    void writeDisplay(String sLine1, String sLine2, String sLine3);

    void writeDisplay(String sLine1, String sLine2, String sLine3, String sLine4);

    void clearDisplay();
}
