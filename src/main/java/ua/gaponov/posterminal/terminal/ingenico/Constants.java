package ua.gaponov.posterminal.terminal.ingenico;

import java.util.List;

public class Constants {

    public static final int DELAY_TERMINAL_ANSWER_TRANSACTION = 120;
    public static final String TERMINAL_DATA_ENCODING = "ASCII";

    public static final int TERMINAL_ANSWER_COMPLETE_SIZE = 83;  // STX + 2 char + 1 char + 8 char + 1 char + 55 char + 3 char + 10 char + ETX + LRC
    public static final int TERMINAL_ANSWER_LIMITED_SIZE = 28;  // STX + 2 char + 1 char + 8 char + 1 char + 3 char + 10 char + ETX + LRC
    public static final int TERMINAL_ASK_REQUIRED_SIZE = 34;
    public static final String TERMINAL_ANSWER_SET_FULLSIZED = "1";
    public static final String TERMINAL_ANSWER_SET_SMALLSIZED = "0";

    public static final TerminalTransactionTypes TERMINAL_MODE_PAYMENT_DEBIT = TerminalTransactionTypes.DEBIT;
    public static final TerminalTransactionTypes TERMINAL_MODE_PAYMENT_CREDIT = TerminalTransactionTypes.CREDIT;
    public static final TerminalTransactionTypes TERMINAL_MODE_PAYMENT_REFUND = TerminalTransactionTypes.REFUND;

    public static final String TERMINAL_TYPE_PAYMENT_CARD = "1";

    public static final String TERMINAL_NUMERIC_CURRENCY_UAH = "980";

    public static final String TERMINAL_REQUEST_ANSWER_WAIT_FOR_TRANSACTION = "A010";
    public static final String TERMINAL_REQUEST_ANSWER_INSTANT = "A011";

    public static final String TERMINAL_FORCE_AUTHORIZATION_ENABLE = "B011";
    public static final String TERMINAL_FORCE_AUTHORIZATION_DISABLE = "B010";

    public static final List<String> CONTROL_NAMES = List.of(
            "NUL", "SOH", "STX", "ETX", "EOT", "ENQ", "ACK", "BEL",
            "BS", "HT", "LF", "VT", "FF", "CR", "SO", "SI",
            "DLE", "DC1", "DC2", "DC3", "DC4", "NAK", "SYN", "ETB",
            "CAN", "EM", "SUB", "ESC", "FS", "GS", "RS", "US",
            "SP"
    );
}
