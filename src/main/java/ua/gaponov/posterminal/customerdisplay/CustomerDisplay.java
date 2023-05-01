package ua.gaponov.posterminal.customerdisplay;

public interface CustomerDisplay {
    public void repaintLines();
    public void writeDisplay(int animation, String sLine1, String sLine2);
    public void writeDisplay(String sLine1, String sLine2);
    public void clearDisplay();
}
