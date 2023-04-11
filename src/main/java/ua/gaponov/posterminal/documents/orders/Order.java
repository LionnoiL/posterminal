package ua.gaponov.posterminal.documents.orders;

import java.time.LocalDateTime;
import ua.gaponov.posterminal.cards.Card;

/**
 *
 * @author wmcon
 */
public class Order {
    private String guid;
    private LocalDateTime date;
    private boolean upload;
    private double documentSum;
    private double paySum;
    private PayTypes payType; 
    private boolean fiscal;
    private boolean internet;
    private boolean fiscalPrint;
    private Card card;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isUpload() {
        return upload;
    }

    public void setUpload(boolean upload) {
        this.upload = upload;
    }

    public double getDocumentSum() {
        return documentSum;
    }

    public void setDocumentSum(double documentSum) {
        this.documentSum = documentSum;
    }

    public double getPaySum() {
        return paySum;
    }

    public void setPaySum(double paySum) {
        this.paySum = paySum;
    }

    public PayTypes getPayType() {
        return payType;
    }

    public void setPayType(PayTypes payType) {
        this.payType = payType;
    }

    public boolean isFiscal() {
        return fiscal;
    }

    public void setFiscal(boolean fiscal) {
        this.fiscal = fiscal;
    }

    public boolean isInternet() {
        return internet;
    }

    public void setInternet(boolean internet) {
        this.internet = internet;
    }

    public boolean isFiscalPrint() {
        return fiscalPrint;
    }

    public void setFiscalPrint(boolean fiscalPrint) {
        this.fiscalPrint = fiscalPrint;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
