package ua.gaponov.posterminal.barcodes;

import ua.gaponov.posterminal.products.Product;

/**
 *
 * @author wmcon
 */
public class Barcode {
    
    private String barcode;
    private Product product;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
}
