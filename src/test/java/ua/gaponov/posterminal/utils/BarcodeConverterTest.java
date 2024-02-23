package ua.gaponov.posterminal.utils;

import org.junit.jupiter.api.Assertions;
import ua.gaponov.posterminal.entity.products.Product;
import ua.gaponov.posterminal.entity.products.ProductService;

class BarcodeConverterTest {

    @org.junit.jupiter.api.Test
    void convertToLatin() {
        String cyrBarcode = "ФУФЧ276079";
        String latBarcode = "AEAX276079";

        String res = BarcodeConverter.convertToLatin(cyrBarcode);
        Assertions.assertEquals(latBarcode, res);
    }
}