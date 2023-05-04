package ua.gaponov.posterminal.devices.terminal.ingenico;

import ua.gaponov.posterminal.devices.exceptions.SequenceDoesNotMatchLengthException;
import ua.gaponov.posterminal.utils.StringUtils;

import static ua.gaponov.posterminal.devices.terminal.ingenico.Constants.TERMINAL_ASK_REQUIRED_SIZE;

/**
 * @author Andriy Gaponov
 */
public class TeliumAsk extends TeliumData {

    private String answerFlag;
    private TerminalTransactionTypes transactionType;
    private String delay;
    private String authorization;

    public TeliumAsk(String posNumber,
                     String answerFlag,
                     TerminalTransactionTypes transactionType,
                     String paymentMode,
                     String currencyNumeric,
                     String delay,
                     String authorization,
                     double amount) {

        super(posNumber, amount, paymentMode, currencyNumeric, "          ");
        this.answerFlag = answerFlag;
        this.transactionType = transactionType;
        this.delay = delay;
        this.authorization = authorization;

    }

    public String getAnswerFlag() {
        return answerFlag;
    }

    public TerminalTransactionTypes getTransactionType() {
        return transactionType;
    }

    public String getDelay() {
        return delay;
    }

    public String getAuthorization() {
        return authorization;
    }

    public String encode() throws SequenceDoesNotMatchLengthException {
        StringBuilder sb = new StringBuilder();
        sb.append(getCurrencyNumeric());
        sb.append(StringUtils.padLeftZeros(String.valueOf(getAmount() * 100), 8));
        sb.append(getAnswerFlag());
        sb.append(getPaymentMode());
        sb.append(getTransactionType().getValue());
        sb.append(getCurrencyNumeric());
        sb.append(getReserved());
        sb.append(getDelay());
        sb.append(getAuthorization());

        String result = sb.toString();
        int packetLength = result.length();
        if (packetLength != TERMINAL_ASK_REQUIRED_SIZE) {
            throw new SequenceDoesNotMatchLengthException("Cannot create ask payment sequence with len != " +
                    TERMINAL_ASK_REQUIRED_SIZE + " octets. " +
                    "Currently have " + packetLength + " octet(s)."
            );
        }
        return TeliumData.framing(result);
    }
}
