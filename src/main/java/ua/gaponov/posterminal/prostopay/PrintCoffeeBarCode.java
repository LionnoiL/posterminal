package ua.gaponov.posterminal.prostopay;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.devices.printer.Printer;
import ua.gaponov.posterminal.products.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;

/**
 * @author Andriy Gaponov
 */
public class PrintCoffeeBarCode implements Printable {

    private static final Logger LOG = LoggerFactory.getLogger(PrintCoffeeBarCode.class);
    private final Product product;
    private final String qrCodeText;
    private Printer printer = new Printer(130, 1000, 0, 0, this);

    public PrintCoffeeBarCode(Product product, String qrCodeText) {
        this.product = product;
        this.qrCodeText = qrCodeText;

        try {
            printer.print();
        } catch (PrinterException ex) {
            LOG.error("Printing ProstoPay qr-code failed", ex);
            JOptionPane.showMessageDialog(null, "Printing Failed, Error: " + ex);
        }
    }

    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 140, 140);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        printer.setG2d((Graphics2D) graphics);

        printer.printCenter(product.getName(), 8);
        try {
            printer.printImage(generateQRCodeImage(qrCodeText));
        } catch (Exception ex) {
            LOG.error("Generate ProstoPay qr-code failed", ex);
            throw new RuntimeException(ex);
        }
        return 0;
    }

}
