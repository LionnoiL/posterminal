package ua.gaponov.posterminal.dataexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.entity.barcodes.Barcode;
import ua.gaponov.posterminal.entity.barcodes.BarcodeService;
import ua.gaponov.posterminal.entity.barcodes.BarcodeXmlBuilder;
import ua.gaponov.posterminal.entity.cards.Card;
import ua.gaponov.posterminal.entity.cards.CardService;
import ua.gaponov.posterminal.entity.cards.CardXmlBuilder;
import ua.gaponov.posterminal.entity.organization.Organization;
import ua.gaponov.posterminal.entity.organization.OrganizationService;
import ua.gaponov.posterminal.entity.organization.OrganizationXmlBuilder;
import ua.gaponov.posterminal.entity.products.Product;
import ua.gaponov.posterminal.entity.products.ProductService;
import ua.gaponov.posterminal.entity.products.ProductXmlBuilder;
import ua.gaponov.posterminal.entity.quickproduct.QuickProduct;
import ua.gaponov.posterminal.entity.quickproduct.QuickProductService;
import ua.gaponov.posterminal.entity.quickproduct.QuickProductXmlBuilder;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.utils.XmlUtils;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Andriy Gaponov
 */
public class ExchangeDownloader {

    public static final String IMPORT_FILE = "files/import.xml";
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeDownloader.class);

    public static void download() throws Exception {
        LOG.info("Start import data");

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
            //TODO: delete file
            AppProperties.exchangeRunning = false;
        }

        LOG.info("End import data");
    }
}
