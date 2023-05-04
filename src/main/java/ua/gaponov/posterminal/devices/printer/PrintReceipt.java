package ua.gaponov.posterminal.devices.printer;

import ua.gaponov.posterminal.documents.orders.Order;
import ua.gaponov.posterminal.documents.orders.OrderDetail;
import ua.gaponov.posterminal.organization.Organization;
import ua.gaponov.posterminal.utils.AppProperties;
import ua.gaponov.posterminal.utils.DateUtils;
import ua.gaponov.posterminal.utils.RoundUtils;
import ua.gaponov.posterminal.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.util.List;
import java.util.Map;

/**
 * @author Andriy Gaponov
 */
public class PrintReceipt implements Printable {

    private final int MARGIN = 0;
    private final int PAPER_WIDTH = 130;
    private final int PAPER_HEIGHT = 1000;
    private final int FONT_MARGIN = 10;
    private final Order order;
    private PrinterJob printerJob;
    private PageFormat pageFormat;
    private Paper paper;
    private int currentLine;

    public PrintReceipt(Order order) {

        this.order = order;

        currentLine = -120 + -30 * order.getDetails().size();

        printerJob = PrinterJob.getPrinterJob();
        pageFormat = printerJob.defaultPage();

        paper = new Paper();
        paper.setImageableArea(MARGIN, -1000, PAPER_WIDTH, PAPER_HEIGHT);
        pageFormat.setPaper(paper);
        pageFormat.setOrientation(PageFormat.PORTRAIT);
        printerJob.setPrintable(this, pageFormat);

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

        printTitle(g2d);
        printProducts(g2d);
        printOrganizations(g2d);
        printTotals(g2d);
        printPays(g2d);
        printCardInfo(g2d);
        printFooter(g2d);


        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        return PAGE_EXISTS;
    }

    private void printString(Graphics2D g2d,
                             int fontSize,
                             boolean bold,
                             Align align,
                             boolean wrap,
                             String text,
                             boolean addLine
    ) {
        int fontStyle = bold ? Font.BOLD : Font.PLAIN;
        g2d.setFont(new Font("Consolas", fontStyle, fontSize));

        int textWidth = g2d.getFontMetrics().stringWidth(text);
        int x = 0;

        switch (align) {
            case CENTER:
                x = (PAPER_WIDTH - textWidth) / 2;
                break;
            case RIGHT:
                x = PAPER_WIDTH - textWidth;
                break;
            default:
                x = 0;
        }
        if (wrap) {
            int fontWidth = textWidth / text.length();
            int blockWidth = PAPER_WIDTH - x;
            int fontCount = blockWidth / fontWidth;

            String[] lineStrings = StringUtils.splitStringByCharCount(text, fontCount);
            for (String lineString : lineStrings) {
                drawString(g2d, x, currentLine, lineString);
                if (addLine) {
                    currentLine = currentLine + FONT_MARGIN;
                }
            }
        } else {
            drawString(g2d, x, currentLine, text);
            if (addLine) {
                currentLine = currentLine + FONT_MARGIN;
            }
        }

    }

    private void drawString(Graphics2D g2d, int x, int y, String text) {
        g2d.drawString(text, x, y);
    }

    private void printHorizontalLine(Graphics2D g2d) {
        g2d.drawLine(0, currentLine, PAPER_WIDTH, currentLine);
        currentLine = currentLine + FONT_MARGIN;
    }

    private void printTitle(Graphics2D g2d) {
        printString(g2d, 10, true, Align.CENTER, false, AppProperties.shopName, true);
        printString(g2d, 8, false, Align.CENTER, true, AppProperties.shopAddress, true);
        printString(g2d, 6, false, Align.LEFT, false, DateUtils.getDateTimeNow(), false);
        printString(g2d, 6, false, Align.RIGHT, false, "Продаж", true);
        printString(g2d, 8, false, Align.CENTER, false, "Товарний чек №" + order.getOrderNumber(), true);
        printString(g2d, 8, false, Align.CENTER, false, AppProperties.cashRegisterName, true);
        printHorizontalLine(g2d);
    }

    private void printOrganizations(Graphics2D g2d) {
        Map<Organization, Double> totalsByOrganizations = order.getTotalsByOrganizations();
        for (Map.Entry<Organization, Double> organizationDoubleEntry : totalsByOrganizations.entrySet()) {
            if (organizationDoubleEntry.getKey() == null) {
                printString(g2d, 6, false, Align.LEFT, false, "", false);
            } else {
                printString(g2d, 6, false, Align.LEFT, false, organizationDoubleEntry.getKey().getName(), false);
            }
            printString(g2d, 6, false, Align.RIGHT, false, String.valueOf(RoundUtils.round(organizationDoubleEntry.getValue())), true);
        }
        printHorizontalLine(g2d);
    }

    private void printTotals(Graphics2D g2d) {
        printString(g2d, 8, false, Align.LEFT, false, "ПІДСУМОК", false);
        printString(g2d, 8, false, Align.RIGHT, false,
                RoundUtils.round(order.getDocumentSumWithoutDiscount()) +
                        " " + AppProperties.currency, true);
    }

    private void printPays(Graphics2D g2d) {
        printString(g2d, 8, false, Align.LEFT, false, "ЗНИЖКА СКЛАЛА", false);
        printString(g2d, 8, false, Align.RIGHT, false,
                RoundUtils.round(order.getDiscountSum()) +
                        " " + AppProperties.currency, true);

        printString(g2d, 8, false, Align.LEFT, false, "ОКРУГЛЕННЯ", false);
        String sign = "+";
        if (order.getRoundSum() > 0) {
            sign = "-";
        }
        printString(g2d, 8, false, Align.RIGHT, false,
                sign + Math.abs(RoundUtils.round(order.getRoundSum())) +
                        " " + AppProperties.currency, true);

        printString(g2d, 10, false, Align.LEFT, false, "ДО СПЛАТИ", false);
        printString(g2d, 10, false, Align.RIGHT, false,
                RoundUtils.round(order.getToPaySum()) +
                        " " + AppProperties.currency, true);
    }

    private void printCardInfo(Graphics2D g2d) {
        if (order.getCard() != null) {
            printString(g2d, 8, true, Align.CENTER, false,
                    "Поточна знижка по картці " + order.getCard().getDiscount() + "%", true
            );
        }
    }

    private void printFooter(Graphics2D g2d) {
        printString(g2d, 10, true, Align.CENTER, false, "Дякуємо за покупку", true);
    }

    private void printProducts(Graphics2D g2d) {
        List<OrderDetail> details = order.getDetails();
        for (OrderDetail detail : details) {
            printString(g2d, 8, false, Align.LEFT, true, detail.getProduct().getName().toUpperCase(), true);
            printString(g2d, 8, false, Align.RIGHT, false,
                    detail.getQty() +
                            "(" + detail.getProduct().getUnitName() + ")x" +
                            detail.getPrice() +
                            "=" +
                            RoundUtils.round(detail.getSummaWithoutDiscount()), true
            );
        }
        printHorizontalLine(g2d);
    }

    public enum Align {
        LEFT, CENTER, RIGHT
    }
}
