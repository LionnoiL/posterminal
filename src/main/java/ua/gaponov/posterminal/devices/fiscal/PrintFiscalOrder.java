package ua.gaponov.posterminal.devices.fiscal;

import jakarta.xml.bind.DatatypeConverter;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.devices.printer.Printer;

/**
 * @author Andriy Gaponov
 */
public class PrintFiscalOrder implements Printable {

    private static final Logger LOG = LoggerFactory.getLogger(PrintFiscalOrder.class);
    private Printer printer = new Printer(130, 1000, 0, 0, this);
    private String base64Image;

    public PrintFiscalOrder(String base64Image) {
        this.base64Image = base64Image;
        try {
            printer.print();
        } catch (PrinterException ex) {
            LOG.error("Printing order failed", ex);
            JOptionPane.showMessageDialog(null, "Printing Failed, Error: " + ex.toString());
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        printer.setG2d((Graphics2D) graphics);

        printOrder();

        return PAGE_EXISTS;
    }

    private void printOrder() {

        try {
            String[] strings = base64Image.split(",");
            byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
            InputStream is = new ByteArrayInputStream(data);
            BufferedImage image = ImageIO.read(is);
            printer.printImage(image, true);
        } catch (Exception ex) {
            //NOP
        }
    }
}
