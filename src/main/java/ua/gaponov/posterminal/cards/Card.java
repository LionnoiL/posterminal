package ua.gaponov.posterminal.cards;

/**
 *
 * @author gaponov
 */
public class Card {
    private String guid;
    private String code;
    private boolean active;
    private CardType cardType;
    private String clientName;
    private String clientPhone;
    private String clientEmail;
    private double discount;
    private double debt;
    private boolean debtAllowed;
    private double maxDebt;
    private int maxDebtDay;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    public boolean isDebtAllowed() {
        return debtAllowed;
    }

    public void setDebtAllowed(boolean debtAllowed) {
        this.debtAllowed = debtAllowed;
    }

    public double getMaxDebt() {
        return maxDebt;
    }

    public void setMaxDebt(double maxDebt) {
        this.maxDebt = maxDebt;
    }

    public int getMaxDebtDay() {
        return maxDebtDay;
    }

    public void setMaxDebtDay(int maxDebtDay) {
        this.maxDebtDay = maxDebtDay;
    }
}
