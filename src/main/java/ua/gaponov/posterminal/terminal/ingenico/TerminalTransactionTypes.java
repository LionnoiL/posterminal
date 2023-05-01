package ua.gaponov.posterminal.terminal.ingenico;

public enum TerminalTransactionTypes {
    DEBIT("0"),
    CREDIT("1"),
    REFUND("2");

    private String value;

    TerminalTransactionTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
