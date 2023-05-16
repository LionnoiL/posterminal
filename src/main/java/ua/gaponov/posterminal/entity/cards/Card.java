package ua.gaponov.posterminal.entity.cards;

import ua.gaponov.posterminal.entity.products.Product;

import java.beans.Transient;
import java.io.Serializable;

/**
 * @author Andriy Gaponov
 */
public class Card implements Serializable {
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

    public double getDiscountForProduct(Product product) {
        if (product.isBanDisckount()) {
            return 0;
        }
        return getDiscount();
    }

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

    @Transient
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Transient
    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    @Transient
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Transient
    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    @Transient
    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    @Transient
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Transient
    public double getDebt() {
        return debt;
    }

    public void setDebt(double debt) {
        this.debt = debt;
    }

    @Transient
    public boolean isDebtAllowed() {
        return debtAllowed;
    }

    public void setDebtAllowed(boolean debtAllowed) {
        this.debtAllowed = debtAllowed;
    }

    @Transient
    public double getMaxDebt() {
        return maxDebt;
    }

    public void setMaxDebt(double maxDebt) {
        this.maxDebt = maxDebt;
    }

    @Transient
    public int getMaxDebtDay() {
        return maxDebtDay;
    }

    public void setMaxDebtDay(int maxDebtDay) {
        this.maxDebtDay = maxDebtDay;
    }
}
