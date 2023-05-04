package ua.gaponov.posterminal.devices.printer;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import ua.gaponov.posterminal.documents.orders.Order;
import ua.gaponov.posterminal.products.Product;
import ua.gaponov.posterminal.products.ProductService;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.awt.print.*;

/**
 * @author Andriy Gaponov
 */
public class PrintCoffeeBarCode implements Printable {

    private final Product product;
    private final String qrCodeText;

    private final int MARGIN = 0;
    private final int PAPER_WIDTH = 130;
    private final int PAPER_HEIGHT = 1000;
    private PrinterJob printerJob;
    private PageFormat pageFormat;
    private Paper paper;

    public PrintCoffeeBarCode(Product product, String qrCodeText) {
        this.product = product;
        this.qrCodeText = qrCodeText;

        printerJob = PrinterJob.getPrinterJob();
        pageFormat = printerJob.defaultPage();

        paper = new Paper();
        paper.setImageableArea(MARGIN, MARGIN, PAPER_WIDTH, PAPER_HEIGHT);
        pageFormat.setPaper(paper);
        pageFormat.setOrientation(PageFormat.PORTRAIT);
        printerJob.setPrintable(this, pageFormat);
        printerJob.setCopies(1);

        try {
            printerJob.print();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(null, "Printing Failed, Error: " + ex.toString());
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Arial", Font.BOLD, 8));
        g2d.drawString(product.getName(), 10, 10);
        try {
            g2d.drawImage(generateQRCodeImage(qrCodeText), 1, 10, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
                barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 140, 140);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

}
