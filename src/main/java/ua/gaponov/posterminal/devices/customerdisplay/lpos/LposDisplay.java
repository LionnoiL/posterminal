package ua.gaponov.posterminal.devices.customerdisplay.lpos;

import com.fazecast.jSerialComm.SerialPort;
import ua.gaponov.posterminal.devices.customerdisplay.CustomerDisplay;
import ua.gaponov.posterminal.devices.exceptions.SignalDoesNotExistException;
import ua.gaponov.posterminal.utils.AppProperties;
import ua.gaponov.posterminal.utils.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.fazecast.jSerialComm.SerialPort.NO_PARITY;

/**
 * @author Andriy Gaponov
 */
public class LposDisplay implements CustomerDisplay {

    private static final int CHAR_COUNT = 20;
    private SerialPort device;

    public LposDisplay() {
        createDevice();
    }

    private boolean open() {
        if (Objects.isNull(device)) {
            return false;
        }
        return device.openPort();
    }

    private boolean close() {
        if (Objects.isNull(device)) {
            return false;
        }
        return device.closePort();
    }

    private boolean isOpen() {
        if (Objects.isNull(device)) {
            return false;
        }
        return device.isOpen();
    }

    private void sendSignal(String signal) throws SignalDoesNotExistException {
        if (!Constants.CONTROL_NAMES.contains(signal)) {
            throw new SignalDoesNotExistException("The " + Constants.TERMINAL_DATA_ENCODING + signal +
                    " code doesn't exist.");
        }

        int index = Constants.CONTROL_NAMES.indexOf(signal);
        char code = (char) index;
        byte[] charBytes = {(byte) code};
        device.writeBytes(charBytes, 1);
    }

    private void send(String data) {
        byte[] bytes;
        bytes = data.getBytes();
        device.writeBytes(bytes, bytes.length);
    }

    private void send(byte[] data) {
        device.writeBytes(data, data.length);
    }

    private String replaceUkrainianChar(String data) {
        data = data.replace("і", "i");
        data = data.replace("І", "I");
        data = data.replace("ї", "i");
        data = data.replace("ї", "I");
        return data;
    }

    private void sendString(String data) {
        data = replaceUkrainianChar(data);

        List<Character> characters = StringUtils.convertStringToCharList(data);
        byte byteCode;

        for (Character character : characters) {
            int characterCode = (int) character;
            if (characterCode >= 1040) {
                if (Constants.CYRILYK_TABLE.containsKey(characterCode)) {
                    byteCode = Constants.CYRILYK_TABLE.get(characterCode);
                } else {
                    byteCode = 0;
                }
                byte[] bytes = {byteCode};

                send(bytes);
            } else {
                send(String.valueOf(character));
            }
        }
        send((char) Constants.CONTROL_NAMES.indexOf("CR") + "");
    }

    @Override
    public void clearDisplay() {
        try {
            sendSignal("FF");
        } catch (SignalDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeDisplay(String sLine1, String sLine2) {
        open();
        if (isOpen()) {
            clearDisplay();
            writeFirstLine(sLine1);
            writeSecondLine(sLine2);
            close();
        }
    }

    @Override
    public void writeDisplay(String sLine1, String sLine2, String sLine3) {
        open();
        if (isOpen()) {
            clearDisplay();
            writeFirstLine(sLine1);

            int posRight = CHAR_COUNT - sLine3.length();
            int spaceCount = posRight - sLine2.length();
            String spaces = StringUtils.getSpaces(spaceCount);

            writeSecondLine(sLine2 + spaces + sLine3);
            close();
        }
    }

    @Override
    public void writeDisplay(String sLine1, String sLine2, String sLine3, String sLine4) {
        int posRight;
        int spaceCount;

        open();
        if (isOpen()) {
            clearDisplay();

            posRight = CHAR_COUNT - sLine2.length();
            spaceCount = posRight - sLine1.length();
            writeFirstLine(sLine1 + StringUtils.getSpaces(spaceCount) + sLine2);

            posRight = CHAR_COUNT - sLine4.length();
            spaceCount = posRight - sLine3.length();
            writeSecondLine(sLine3 + StringUtils.getSpaces(spaceCount) + sLine4);

            close();
        }
    }

    private void writeFirstLine(String data) {
        if (isOpen()) {
            send((char) Constants.CONTROL_NAMES.indexOf("ESC") + "fR");
            send((char) Constants.CONTROL_NAMES.indexOf("ESC") + "cR");
            send((char) Constants.CONTROL_NAMES.indexOf("ESC") + "QA");
            sendString(data);
        }
    }

    private void writeSecondLine(String data) {
        if (isOpen()) {
            send((char) Constants.CONTROL_NAMES.indexOf("ESC") + "fR");
            send((char) Constants.CONTROL_NAMES.indexOf("ESC") + "cR");
            send((char) Constants.CONTROL_NAMES.indexOf("ESC") + "QB");
            sendString(data);
        }
    }

    private void createDevice() {
        try {
            device = SerialPort.getCommPort(AppProperties.displayPort);
            device.setBaudRate(9600);
            device.setNumDataBits(8);
            device.setParity(NO_PARITY);
            device.setNumStopBits(1);
        } catch (Exception ex) {
            //NOP
        }
    }
}
