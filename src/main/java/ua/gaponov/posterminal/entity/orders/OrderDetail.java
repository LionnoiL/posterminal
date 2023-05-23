package ua.gaponov.posterminal.entity.orders;

import lombok.Getter;
import lombok.Setter;
import ua.gaponov.posterminal.entity.cards.Card;
import ua.gaponov.posterminal.entity.organization.Organization;
import ua.gaponov.posterminal.entity.products.Product;
import ua.gaponov.posterminal.utils.RoundUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
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
    private Organization organization;
    private boolean fiscalPrint;

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
}
