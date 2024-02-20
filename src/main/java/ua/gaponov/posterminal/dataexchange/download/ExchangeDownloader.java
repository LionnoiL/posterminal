package ua.gaponov.posterminal.dataexchange.download;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.dataexchange.ExchangeBuilder;
import ua.gaponov.posterminal.entity.barcodes.Barcode;
import ua.gaponov.posterminal.entity.barcodes.BarcodeService;
import ua.gaponov.posterminal.entity.barcodes.BarcodeXmlBuilder;
import ua.gaponov.posterminal.entity.cards.Card;
import ua.gaponov.posterminal.entity.cards.CardService;
import ua.gaponov.posterminal.entity.cards.CardXmlBuilder;
import ua.gaponov.posterminal.entity.messages.ExchangeMessage;
import ua.gaponov.posterminal.entity.messages.ExchangeMessageService;
import ua.gaponov.posterminal.entity.messages.ExchangeMessageXmlBuilder;
import ua.gaponov.posterminal.entity.options.OptionsValue;
import ua.gaponov.posterminal.entity.options.OptionsValueService;
import ua.gaponov.posterminal.entity.organization.Organization;
import ua.gaponov.posterminal.entity.organization.OrganizationService;
import ua.gaponov.posterminal.entity.organization.OrganizationXmlBuilder;
import ua.gaponov.posterminal.entity.products.Product;
import ua.gaponov.posterminal.entity.products.ProductService;
import ua.gaponov.posterminal.entity.products.ProductXmlBuilder;
import ua.gaponov.posterminal.entity.quickproduct.QuickProduct;
import ua.gaponov.posterminal.entity.quickproduct.QuickProductService;
import ua.gaponov.posterminal.entity.quickproduct.QuickProductXmlBuilder;
import ua.gaponov.posterminal.utils.FilesUtils;
import ua.gaponov.posterminal.utils.XmlUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExchangeDownloader {

    public static final String IMPORT_FILE = AppProperties.getExchangeFolder() + "import_" + AppProperties.getArmId() + ".xml";
    private static final Logger LOG = LoggerFactory.getLogger(ExchangeDownloader.class);
    private static final ExchangeBuilder<Organization, XmlUtils> ORGANIZATION_BUILDER = new OrganizationXmlBuilder();
    private static final ExchangeBuilder<ExchangeMessage, XmlUtils> MESSAGE_BUILDER = new ExchangeMessageXmlBuilder();
    private static final ExchangeBuilder<Product, XmlUtils> PRODUCT_BUILDER = new ProductXmlBuilder();
    private static final ExchangeBuilder<Barcode, XmlUtils> BARCODE_BUILDER = new BarcodeXmlBuilder();
    private static final ExchangeBuilder<Card, XmlUtils> CARD_BUILDER = new CardXmlBuilder();
    private static final ExchangeBuilder<QuickProduct, XmlUtils> QUICK_PRODUCT_BUILDER = new QuickProductXmlBuilder();

    public static void download() throws UpdateDownloadException {
        if (!FilesUtils.fileExist(IMPORT_FILE)) {
            return;
        }
        File f = new File(IMPORT_FILE);
        if (!f.canRead()) {
            return;
        }

        LOG.info("Start import data");
        Path path = Paths.get(IMPORT_FILE);
        try (InputStream is = Files.newInputStream(path);
             XmlUtils processor = new XmlUtils(is)) {

            AppProperties.exchangeRunning = true;
            QuickProductService.deleteAll();

            while (processor.startElement("message", "messages")) {
                ExchangeMessage message = MESSAGE_BUILDER.create(processor);
                ExchangeMessageService.saveMessages(message);
            }

            while (processor.startElement("organization", "organizations")) {
                Organization organization = ORGANIZATION_BUILDER.create(processor);
                OrganizationService.save(organization);
            }

            while (processor.startElement("product", "products")) {
                Product product = PRODUCT_BUILDER.create(processor);
                ProductService.clearSkuCode(product.getSku());
                ProductService.save(product);
            }

            while (processor.startElement("ean", "eans")) {
                Barcode barcode = BARCODE_BUILDER.create(processor);
                if (barcode.getProduct() != null){
                    BarcodeService.save(barcode);
                }
            }

            while (processor.startElement("discounts_card", "discounts_cards")) {
                Card card = CARD_BUILDER.create(processor);
                CardService.save(card);
            }

            while (processor.startElement("quick_product", "quick_products")) {
                QuickProduct quickProduct = QUICK_PRODUCT_BUILDER.create(processor);
                if (quickProduct.getProduct() != null){
                    QuickProductService.save(quickProduct);
                }
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            OptionsValue optionsValue = OptionsValue.builder()
                    .key("last_update")
                    .value(now.format(formatter))
                    .build();
            OptionsValueService.updateOptionsValue(optionsValue);

            AppProperties.exchangeRunning = false;
        } catch (Exception e){
            LOG.error("Failed download updates");
            LOG.error(e.toString());
            throw new UpdateDownloadException("Failed download updates");
        }
        try {
            FilesUtils.deleteFile(IMPORT_FILE);
        } catch (IOException e) {
            LOG.error("Failed delete import file");
        }
        LOG.info("End import data");
    }
}
