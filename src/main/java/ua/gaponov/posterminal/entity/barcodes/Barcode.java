package ua.gaponov.posterminal.entity.barcodes;

import lombok.Getter;
import lombok.Setter;
import ua.gaponov.posterminal.entity.products.Product;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
public class Barcode {

    private String barCodeValue;
    private Product product;
}
