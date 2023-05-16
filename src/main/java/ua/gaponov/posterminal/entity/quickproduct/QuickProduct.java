package ua.gaponov.posterminal.entity.quickproduct;

import ua.gaponov.posterminal.entity.products.Product;

/**
 * @author Andriy Gaponov
 */
public class QuickProduct {

    private Product product;
    private int position;
    private String color;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
