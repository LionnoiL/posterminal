package ua.gaponov.posterminal.printer;

import ua.gaponov.posterminal.AppProperties;
import ua.gaponov.posterminal.documents.orders.Order;
import ua.gaponov.posterminal.documents.orders.OrderDetail;
import ua.gaponov.posterminal.utils.DateUtils;
import ua.gaponov.posterminal.utils.RoundUtils;
import ua.gaponov.posterminal.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.time.LocalDateTime;
import java.util.List;

public class PrintReceipt implements Printable {

    private final int MARGIN = 1;
    private final int PAPER_WIDTH = 140;
    private final int PAPER_HEIGHT = 1000;
    private final int FONT_MARGIN = 10;
    private final Order order;
    private PrinterJob printerJob;
    private PageFormat pageFormat;
    private Paper paper;
    private int currentLine = -50;



    public PrintReceipt(Order order) {

        this.order = order;

        printerJob = PrinterJob.getPrinterJob();
        pageFormat = printerJob.defaultPage();

        paper = new Paper();
        paper.setImageableArea(MARGIN, MARGIN, PAPER_WIDTH, PAPER_HEIGHT);
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

    private void printString(Graphics2D g2d, int fontSize, boolean bold, Align align, boolean wrap, String text) {
        int fontStyle = bold ? Font.BOLD : Font.PLAIN;
        g2d.setFont(new Font("Consolas", fontStyle, fontSize));

        int textWidth = g2d.getFontMetrics().stringWidth(text);
        int x = 0;

        switch (align){
            case CENTER:
                x = (PAPER_WIDTH - textWidth)/2;
                break;
            case RIGHT:
                x = PAPER_WIDTH - textWidth;
                break;
            default:
                x = 0;
        }
        if (wrap){
            int fontWidth = textWidth / text.length();
            int blockWidth = PAPER_WIDTH - x;
            int fontCount = blockWidth / fontWidth;

            String[] lineStrings = StringUtils.splitStringByCharCount(text, fontCount);
            for (String lineString : lineStrings) {
                drawString(g2d, x, currentLine, lineString);
                currentLine = currentLine + FONT_MARGIN;
            }
        } else {
            drawString(g2d, x, currentLine, text);
            currentLine = currentLine + FONT_MARGIN;
        }

    }

    private void drawString(Graphics2D g2d, int x, int y, String text){
        g2d.drawString(text, x, y);
    }

    private void printHorizontalLine(Graphics2D g2d) {
        g2d.drawLine(0, currentLine, PAPER_WIDTH, currentLine);
        currentLine = currentLine + FONT_MARGIN;
    }

    private void printTitle(Graphics2D g2d) {
        printString(g2d, 10, true, Align.CENTER, false, AppProperties.shopName);
        printString(g2d, 8, false, Align.CENTER, true,AppProperties.shopAddress);
        printString(g2d, 6, false, Align.LEFT, false, DateUtils.getDateTimeNow());
        currentLine = currentLine - FONT_MARGIN;
        printString(g2d, 6, false, Align.RIGHT, false, "Продаж");
        printString(g2d, 8, false, Align.LEFT, false,"Товарний чек №1002154"); //TODO номер чека
        printHorizontalLine(g2d);
    }

    private void printOrganizations(Graphics2D g2d) {

    }

    private void printTotals(Graphics2D g2d) {

    }

    private void printPays(Graphics2D g2d) {

    }

    private void printCardInfo(Graphics2D g2d) {
        if (order.getCard()!=null){
            printString(g2d, 8, true, Align.CENTER, false,
                    "Поточна знижка по картці "+order.getCard().getDiscount()+"%"
            );
        }
    }

    private void printFooter(Graphics2D g2d) {
        printString(g2d, 10, true, Align.CENTER, false,"Дякуємо за покупку");
    }

    private void printProducts(Graphics2D g2d) {
        List<OrderDetail> details = order.getDetails();
        for (OrderDetail detail : details) {
            printString(g2d, 8, false, Align.LEFT, true,detail.getProduct().getName().toUpperCase());
            printString(g2d, 8, false, Align.RIGHT,false,
                    detail.getQty() +
                    "(шт)x" +
                    detail.getPrice() +
                    "= " +
                    RoundUtils.round(detail.getSumma())
            );
        }
        printHorizontalLine(g2d);
    }

    public enum Align{
        LEFT, CENTER, RIGHT
    }
}
