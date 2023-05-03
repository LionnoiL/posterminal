package ua.gaponov.posterminal.barcodes;

import ua.gaponov.posterminal.products.Product;

/**
 *
 * @author wmcon
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
