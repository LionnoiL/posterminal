package ua.gaponov.posterminal.dataexchange;

import com.sun.nio.file.ExtendedOpenOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.entity.barcodes.Barcode;
import ua.gaponov.posterminal.entity.barcodes.BarcodeService;
import ua.gaponov.posterminal.entity.barcodes.BarcodeXmlBuilder;
import ua.gaponov.posterminal.entity.cards.Card;
import ua.gaponov.posterminal.entity.cards.CardService;
import ua.gaponov.posterminal.entity.cards.CardXmlBuilder;
import ua.gaponov.posterminal.entity.messages.ExchangeMessage;
import ua.gaponov.posterminal.entity.messages.ExchangeMessageService;
import ua.gaponov.posterminal.entity.messages.ExchangeMessageXmlBuilder;
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
import ua.gaponov.posterminal.utils.FilesUtils;
import ua.gaponov.posterminal.utils.XmlUtils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author Andriy Gaponov
 */
public class ExchangeDownloader {

    public static final String IMPORT_FILE = AppProperties.getExchangeFolder() + "import_"+AppProperties.getArmId()+".xml";
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeDownloader.class);
    private static final ExchangeBuilder<Organization, XmlUtils> organizationBuilder = new OrganizationXmlBuilder();
    private static final ExchangeBuilder<ExchangeMessage, XmlUtils> messageBuilder = new ExchangeMessageXmlBuilder();
    private static final ExchangeBuilder<Product, XmlUtils> productBuilder = new ProductXmlBuilder();
    private static final ExchangeBuilder<Barcode, XmlUtils> barcodeBuilder = new BarcodeXmlBuilder();
    private static final ExchangeBuilder<Card, XmlUtils> cardBuilder = new CardXmlBuilder();
    private static final ExchangeBuilder<QuickProduct, XmlUtils> quickProductBuilder = new QuickProductXmlBuilder();

    public static void download() throws Exception {
        if (!FilesUtils.fileExist(IMPORT_FILE)) {
            return;
        }
        File f = new File(IMPORT_FILE);
        if (!f.canRead()){
            return;
        }

        LOG.info("Start import data");
        Path path = Paths.get(IMPORT_FILE);
        try (InputStream is = Files.newInputStream(path);
             XmlUtils processor = new XmlUtils(is)) {

            AppProperties.exchangeRunning = true;
            while (processor.startElement("message", "messages")) {
                ExchangeMessage message = messageBuilder.create(processor);
                ExchangeMessageService.saveMessages(message);
            }

            while (processor.startElement("organization", "organizations")) {
                Organization organization = organizationBuilder.create(processor);
                OrganizationService.save(organization);
            }

            while (processor.startElement("product", "products")) {
                Product product = productBuilder.create(processor);
                ProductService.save(product);
            }

            while (processor.startElement("ean", "eans")) {
                Barcode barcode = barcodeBuilder.create(processor);
                BarcodeService.save(barcode);
            }

            while (processor.startElement("discounts_card", "discounts_cards")) {
                Card card = cardBuilder.create(processor);
                CardService.save(card);
            }

            while (processor.startElement("quick_product", "quick_products")) {
                QuickProduct quickProduct = quickProductBuilder.create(processor);
                QuickProductService.save(quickProduct);
            }

            AppProperties.exchangeRunning = false;
        }
        FilesUtils.deleteFile(IMPORT_FILE);
        LOG.info("End import data");
    }
}
