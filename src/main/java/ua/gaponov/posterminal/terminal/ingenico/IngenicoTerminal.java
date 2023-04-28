package ua.gaponov.posterminal.terminal.ingenico;

import com.fazecast.jSerialComm.SerialPort;
import ua.gaponov.posterminal.terminal.Terminal;

import java.io.OutputStream;
import java.util.List;

public class IngenicoTerminal implements Terminal {

    private SerialPort device;

    private final List<String> CONTROL_NAMES = List.of(
            "NUL", "SOH", "STX", "ETX", "EOT", "ENQ", "ACK", "BEL",
            "BS", "HT", "LF", "VT", "FF", "CR", "SO", "SI",
            "DLE", "DC1", "DC2", "DC3", "DC4", "NAK", "SYN", "ETB",
            "CAN", "EM", "SUB", "ESC", "FS", "GS", "RS", "US",
            "SP"
    );


    @Override
    public boolean pay(double summa) {
        try {
           createDevice();
           open();
            sendSignal("ENQ");

           close();
           return true;
        } catch (Exception e){
            return false;
        }
    }

    private void createDevice(){
        device = SerialPort.getCommPort("COM1");
    }

    private boolean open(){
        return device.openPort();
    }

    private boolean close(){
        return device.closePort();
    }

    private boolean isOpen(){
        return device.isOpen();
    }

    private void sendSignal(String signal){
        if (!CONTROL_NAMES.contains(signal)){
            //exception
        }

        int index = CONTROL_NAMES.indexOf(signal);
        char code = (char) index;
        byte[] charBytes = {(byte) code};
        device.writeBytes(charBytes, 1);
    }
}
