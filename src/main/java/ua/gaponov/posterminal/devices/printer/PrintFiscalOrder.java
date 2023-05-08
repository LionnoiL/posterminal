package ua.gaponov.posterminal.devices.printer;

import jakarta.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Andriy Gaponov
 */
public class PrintFiscalOrder implements Printable {

    private static final Logger LOG = LoggerFactory.getLogger(PrintFiscalOrder.class);

    private final int MARGIN = 0;
    private final int PAPER_WIDTH = 130;
    private final int PAPER_HEIGHT = 1000;
    private PrinterJob printerJob;
    private PageFormat pageFormat;
    private Paper paper;

    private String base64Image;
    private Graphics2D g2d;

    public PrintFiscalOrder(String base64Image) {
        this.base64Image = base64Image;

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
            LOG.error("Printing order failed", ex);
            JOptionPane.showMessageDialog(null, "Printing Failed, Error: " + ex.toString());
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        g2d = (Graphics2D) graphics;
        g2d.setColor(Color.black);

        printOrder();

        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        return PAGE_EXISTS;
    }

    private void printOrder() {

        try {
            String[] strings = base64Image.split(",");
            byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);

            InputStream is = new ByteArrayInputStream(data);
            BufferedImage image = ImageIO.read(is);
            int scale = image.getWidth() / PAPER_WIDTH;

            g2d.drawImage(image, 1, 1, PAPER_WIDTH, image.getHeight() / scale, null);
        } catch (Exception ex) {
            //NOP
        }

    }
}
