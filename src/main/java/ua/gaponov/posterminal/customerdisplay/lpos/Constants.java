package ua.gaponov.posterminal.customerdisplay.lpos;

import ua.gaponov.posterminal.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

    public static final String TERMINAL_DATA_ENCODING = "ASCII";

    public static final List<String> CONTROL_NAMES = List.of(
            "NUL", "SOH", "STX", "ETX", "EOT", "ENQ", "ACK", "BEL",
            "BS", "HT", "LF", "VT", "FF", "CR", "SO", "SI",
            "DLE", "DC1", "DC2", "DC3", "DC4", "NAK", "SYN", "ETB",
            "CAN", "EM", "SUB", "ESC", "FS", "GS", "RS", "US",
            "SP"
    );

    public static final Map<Integer, Byte> CYRILYK_TABLE = new HashMap<>();

    static
    {
        String s = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЪЭЮЯабвгдежзийклмноп" +
                "                                                " +
                "рстуфхцчшщьыъэюя";
        String[] strings = StringUtils.splitStringByCharCount(s, 1);
        for (int i = 0; i < strings.length; i++) {
            CYRILYK_TABLE.put(strings[i].codePointAt(0), (byte) (-128 + i));
        }
    }
}
