package ua.gaponov.posterminal.devices.terminal;

/**
 * @author Andriy Gaponov
 */
public interface Terminal {
    boolean pay(int merchId, double summa);
}
