package ua.gaponov.posterminal.entity.quickproduct;

import lombok.Getter;
import lombok.Setter;
import ua.gaponov.posterminal.entity.products.Product;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
public class QuickProduct {

    private Product product;
    private int position;
    private String color;
}
