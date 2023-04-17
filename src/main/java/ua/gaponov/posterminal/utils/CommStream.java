package ua.gaponov.posterminal.utils;

import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;

public class CommStream {

    private String sPort;

    private OutputStream out;
    private InputStream in;

    public static void listCommPorts(){
        //https://fazecast.github.io/jSerialComm/
        SerialPort[] ports = SerialPort.getCommPorts();

        for (SerialPort port: ports) {
            System.out.println(port.getSystemPortName());
        }
    }
}
