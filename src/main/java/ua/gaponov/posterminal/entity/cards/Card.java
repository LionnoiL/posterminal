package ua.gaponov.posterminal.entity.cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ua.gaponov.posterminal.entity.products.Product;

import java.io.Serializable;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
public class Card implements Serializable {
    private String guid;
    private String code;
    @JsonIgnore
    private boolean active;
    @JsonIgnore
    private CardType cardType;
    private String clientName;
    @JsonIgnore
    private String clientPhone;
    @JsonIgnore
    private String clientEmail;
    @JsonIgnore
    private double discount;
    @JsonIgnore
    private double debt;
    @JsonIgnore
    private boolean debtAllowed;
    @JsonIgnore
    private double maxDebt;
    @JsonIgnore
    private int maxDebtDay;

    public double getDiscountForProduct(Product product) {
        if (product.isBanDisckount()) {
            return 0;
        }
        return getDiscount();
    }
}
