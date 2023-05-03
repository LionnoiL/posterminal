package ua.gaponov.posterminal.devices.terminal.ingenico;

import com.fazecast.jSerialComm.SerialPort;
import ua.gaponov.posterminal.AppProperties;
import ua.gaponov.posterminal.devices.terminal.Terminal;
import ua.gaponov.posterminal.devices.terminal.exceptions.SequenceDoesNotMatchLengthException;
import ua.gaponov.posterminal.devices.terminal.exceptions.SignalDoesNotExistException;
import ua.gaponov.posterminal.devices.terminal.exceptions.TerminalInitializationFailedException;
import ua.gaponov.posterminal.devices.terminal.exceptions.TerminalSerialLinkClosedException;
import ua.gaponov.posterminal.utils.DialogUtils;

import java.io.UnsupportedEncodingException;

import static com.fazecast.jSerialComm.SerialPort.NO_PARITY;
import static ua.gaponov.posterminal.devices.terminal.ingenico.Constants.*;

public class IngenicoTerminal implements Terminal {

    private SerialPort device;

    @Override
    public boolean pay(int merchId, double summa) {
        try {
            createDevice();
            open();
            if (!isOpen()) {
                return false;
            }

            TeliumAsk payment = new TeliumAsk(
                    String.valueOf(merchId),
                    TERMINAL_ANSWER_SET_FULLSIZED,
                    TERMINAL_MODE_PAYMENT_DEBIT,
                    TERMINAL_TYPE_PAYMENT_CARD,
                    TERMINAL_NUMERIC_CURRENCY_UAH,
                    TERMINAL_REQUEST_ANSWER_WAIT_FOR_TRANSACTION,
                    TERMINAL_FORCE_AUTHORIZATION_DISABLE,
                    summa
            );

            boolean ask = ask(payment);
            close();

            return ask;
        } catch (Exception e) {
            if (isOpen()){
                close();
            }
            DialogUtils.error(null, e.getMessage());
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

    private void sendSignal(String signal) throws SignalDoesNotExistException {
        if (!CONTROL_NAMES.contains(signal)) {
            throw new SignalDoesNotExistException("The " + TERMINAL_DATA_ENCODING + signal + " code doesn't exist.");
        }

        int index = CONTROL_NAMES.indexOf(signal);
        char code = (char) index;
        byte[] charBytes = {(byte) code};
        device.writeBytes(charBytes, 1);
    }

    private boolean waitSignal(String signal) {
        int index = CONTROL_NAMES.indexOf(signal);
        char code = (char) index;
        byte signalByte = (byte) code;

        byte[] readBytes = new byte[1];
        device.readBytes(readBytes, 1);
        return readBytes[0] == signalByte;
    }

    private void send(String data) {
        byte[] bytes;
        try {
            bytes = data.getBytes(TERMINAL_DATA_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        device.writeBytes(bytes, bytes.length);
    }

    private boolean isOk() {
        try {
            sendSignal("ENQ");
            if (!waitSignal("ACK")) {
                return false;
            }
            sendSignal("EOT");
        } catch (SignalDoesNotExistException e) {
            return false;
        }
        return true;
    }

    private boolean ask(TeliumAsk teliumAsk) throws
            TerminalSerialLinkClosedException,
            TerminalInitializationFailedException,
            SignalDoesNotExistException,
            SequenceDoesNotMatchLengthException {
        if (!isOpen()) {
            throw new TerminalSerialLinkClosedException("Your device isn\\'t opened yet.");
        }

        sendSignal("ENQ");
        if (!waitSignal("ACK")) {
            throw new TerminalInitializationFailedException("Payment terminal isn't ready to accept data from host. " +
                    "Check if terminal is properly configured or not busy.");
        }

        send(teliumAsk.encode());

        if (!waitSignal("ACK")) {
            return false;
        }

        sendSignal("EOT");

        return true;
    }
}
