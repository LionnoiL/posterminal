package ua.gaponov.posterminal.terminal.ingenico;

import com.fazecast.jSerialComm.SerialPort;
import ua.gaponov.posterminal.AppProperties;
import ua.gaponov.posterminal.terminal.Terminal;

import java.util.List;

import static com.fazecast.jSerialComm.SerialPort.NO_PARITY;

public class IngenicoTerminal implements Terminal {

    private final List<String> CONTROL_NAMES = List.of(
            "NUL", "SOH", "STX", "ETX", "EOT", "ENQ", "ACK", "BEL",
            "BS", "HT", "LF", "VT", "FF", "CR", "SO", "SI",
            "DLE", "DC1", "DC2", "DC3", "DC4", "NAK", "SYN", "ETB",
            "CAN", "EM", "SUB", "ESC", "FS", "GS", "RS", "US",
            "SP"
    );
    private SerialPort device;

    @Override
    public boolean pay(double summa) {
        try {
            createDevice();
            open();
            if (!isOpen()){
                return false;
            }
            sendSignal("ENQ");

            close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void createDevice() {
        device = SerialPort.getCommPort(AppProperties.terminalPort);
        device.setBaudRate(9600);
        device.setNumDataBits(8);
        device.setParity(NO_PARITY);
        device.setNumStopBits(1);
    }

    private boolean open() {
        return device.openPort();
    }

    private boolean close() {
        return device.closePort();
    }

    private boolean isOpen() {
        return device.isOpen();
    }

    private void sendSignal(String signal) {
        if (!CONTROL_NAMES.contains(signal)) {
            //exception
        }

        int index = CONTROL_NAMES.indexOf(signal);
        char code = (char) index;
        byte[] charBytes = {(byte) code};
        device.writeBytes(charBytes, 1);
    }
}
