package ua.gaponov.posterminal.entity.barcodes;

import ua.gaponov.posterminal.entity.products.Product;

/**
 * @author Andriy Gaponov
 */
public class Barcode {

    private String barCodeValue;
    private Product product;

    public String getBarCodeValue() {
        return barCodeValue;
    }

    public void setBarCodeValue(String barCodeValue) {
        this.barCodeValue = barCodeValue;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
