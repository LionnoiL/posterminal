package ua.gaponov.posterminal.dataexchange;

import ua.gaponov.posterminal.AppProperties;
import ua.gaponov.posterminal.cards.Card;
import ua.gaponov.posterminal.cards.CardService;
import ua.gaponov.posterminal.cards.CardXmlBuilder;
import ua.gaponov.posterminal.quickproduct.QuickProduct;
import ua.gaponov.posterminal.quickproduct.QuickProductService;
import ua.gaponov.posterminal.quickproduct.QuickProductXmlBuilder;
import ua.gaponov.posterminal.utils.FilesUtils;
import ua.gaponov.posterminal.utils.XmlUtils;
import java.nio.file.Files;
import java.nio.file.Paths;
import ua.gaponov.posterminal.barcodes.Barcode;
import ua.gaponov.posterminal.barcodes.BarcodeService;
import ua.gaponov.posterminal.barcodes.BarcodeXmlBuilder;
import ua.gaponov.posterminal.organization.Organization;
import ua.gaponov.posterminal.organization.OrganizationService;
import ua.gaponov.posterminal.organization.OrganizationXmlBuilder;
import ua.gaponov.posterminal.products.Product;
import ua.gaponov.posterminal.products.ProductService;
import ua.gaponov.posterminal.products.ProductXmlBuilder;

/**
 *
 * @author wmcon
 */
public class ExchangeDownloader {

    public static final String IMPORT_FILE = "files/import.xml";
    public static void download() throws Exception {

        try (XmlUtils processor = new XmlUtils(Files.newInputStream(Paths.get(IMPORT_FILE)))) {
            AppProperties.exchangeRunning = true;

            while (processor.startElement("organization", "organizations")) {
                Organization organization = OrganizationXmlBuilder.create(processor);
                OrganizationService.save(organization);
            }

            while (processor.startElement("product", "products")) {
                Product product = ProductXmlBuilder.create(processor);
                ProductService.save(product);
            }

            while (processor.startElement("ean", "eans")) {
                Barcode barcode = BarcodeXmlBuilder.create(processor);
                BarcodeService.save(barcode);
            }

            while (processor.startElement("discounts_card", "discounts_cards")) {
                Card card = CardXmlBuilder.create(processor);
                CardService.save(card);
            }

            while (processor.startElement("quick_product", "quick_products")) {
                QuickProduct quickProduct = QuickProductXmlBuilder.create(processor);
                QuickProductService.save(quickProduct);
            }



            //FilesUtils.deleteFile(IMPORT_FILE);
            System.out.println("Finish download products");
            AppProperties.exchangeRunning = false;
        }
    }
}
