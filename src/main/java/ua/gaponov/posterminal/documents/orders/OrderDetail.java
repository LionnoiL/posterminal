package ua.gaponov.posterminal.documents.orders;

import ua.gaponov.posterminal.cards.Card;
import ua.gaponov.posterminal.products.Product;
import ua.gaponov.posterminal.utils.RoundUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Andriy Gaponov
 */
public class OrderDetail implements Serializable {

    private String guid = UUID.randomUUID().toString();
    private int lineNumber;
    private Product product;
    private double qty;
    private double price;
    private double summa;
    private double summaWithoutDiscount;
    private double summaDiscount;
    private String excise;

    public void recalculateDiscountsInRow(Card card) {
        double discountForProduct = 0;
        setSummaWithoutDiscount(getQty() * getPrice());
        if (Objects.nonNull(card)) {
            discountForProduct = card.getDiscountForProduct(getProduct());
        }
        setSummaDiscount(RoundUtils.round(getSummaWithoutDiscount() * discountForProduct / 100)
        );
        setSumma(getSummaWithoutDiscount() - getSummaDiscount());
    }

    public String getExcise() {
        return excise;
    }

    public void setExcise(String excise) {
        this.excise = excise;
    }

    public void recalculateSumma() {
        setSumma(getQty() * getPrice());
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSumma() {
        return summa;
    }

    public void setSumma(double summa) {
        this.summa = summa;
    }

    public double getSummaWithoutDiscount() {
        return summaWithoutDiscount;
    }

    public void setSummaWithoutDiscount(double summaWithoutDiscount) {
        this.summaWithoutDiscount = summaWithoutDiscount;
    }

    public double getSummaDiscount() {
        return summaDiscount;
    }

    public void setSummaDiscount(double summaDiscount) {
        this.summaDiscount = summaDiscount;
    }
}
