package ua.gaponov.posterminal.prostopay;

import com.google.gson.annotations.SerializedName;

/**
 * @author Andriy Gaponov
 */
public class ProstoPayRequest {

    private int pos;
    private int till;
    private int number;
    @SerializedName(value = "created-at")
    private int createdAt = 0;
    private int ttl = 1;
    @SerializedName(value = "ttl-type")
    private int ttlType = 0;
    private int amount;
    @SerializedName(value = "amount-base")
    private int amountBase = 3;
    @SerializedName(value = "plu-from")
    private int pluFrom;
    @SerializedName(value = "plu-to")
    private int pluTo = 0;

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getTill() {
        return till;
    }

    public void setTill(int till) {
        this.till = till;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public int getTtlType() {
        return ttlType;
    }

    public void setTtlType(int ttlType) {
        this.ttlType = ttlType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmountBase() {
        return amountBase;
    }

    public void setAmountBase(int amountBase) {
        this.amountBase = amountBase;
    }

    public int getPluFrom() {
        return pluFrom;
    }

    public void setPluFrom(int pluFrom) {
        this.pluFrom = pluFrom;
    }

    public int getPluTo() {
        return pluTo;
    }

    public void setPluTo(int pluTo) {
        this.pluTo = pluTo;
    }
}
