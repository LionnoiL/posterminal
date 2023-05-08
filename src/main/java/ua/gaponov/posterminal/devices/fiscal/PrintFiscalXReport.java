package ua.gaponov.posterminal.devices.fiscal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.documents.orders.PrintReceipt;
import ua.gaponov.posterminal.utils.DateUtils;
import ua.gaponov.posterminal.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;

/**
 * @author Andriy Gaponov
 */
public class PrintFiscalXReport implements Printable {

    private static final Logger LOG = LoggerFactory.getLogger(PrintFiscalXReport.class);

    private final int MARGIN = 0;
    private final int PAPER_WIDTH = 130;
    private final int PAPER_HEIGHT = 1000;
    private final int FONT_MARGIN = 10;
    private PrinterJob printerJob;
    private PageFormat pageFormat;
    private Paper paper;

    private int currentLine;
    private XReport report;
    private Graphics2D g2d;

    public PrintFiscalXReport(XReport report) {
        this.report = report;

        currentLine = -120;

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
            LOG.error("Printing x-report failed", ex);
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

        printTitle();
        printSales();
        printReturns();
        printMoneysMove();
        printSafe();
        printFooter();

        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        return PAGE_EXISTS;
    }

    private void printTitle() {
        printString( 10, true, PrintReceipt.Align.CENTER, false, AppProperties.shopName, true);
        printString( 12, true, PrintReceipt.Align.CENTER, false, "X-звіт", true);
        printString( 8, false, PrintReceipt.Align.CENTER, false, "", true);
    }

    private void printFooter() {
        printString( 8, false, PrintReceipt.Align.CENTER, false, DateUtils.getDateTimeNow(), true);
        printString( 8, false, PrintReceipt.Align.LEFT, false, AppProperties.cashRegisterName, false);
        printString( 8, false, PrintReceipt.Align.RIGHT, false, AppProperties.fiscalName, true);
    }

    private void printSales(){
        printString( 10, true, PrintReceipt.Align.LEFT, false, "ПРОДАЖ", true);
        printString( 8, false, PrintReceipt.Align.LEFT, false, "Кількість чеків", false);
        printString( 8, false, PrintReceipt.Align.RIGHT, false, String.valueOf(report.getCountSaleReceipts()), true);

        printString( 8, false, PrintReceipt.Align.LEFT, false, "Готівка", false);
        printString( 8, false, PrintReceipt.Align.RIGHT, false, String.valueOf(report.getSaleCash()), true);

        printString( 8, false, PrintReceipt.Align.LEFT, false, "Картка", false);
        printString( 8, false, PrintReceipt.Align.RIGHT, false, String.valueOf(report.getSaleCard()), true);

        printString( 8, false, PrintReceipt.Align.LEFT, false, "Загалом", false);
        printString( 8, false, PrintReceipt.Align.RIGHT, false, String.valueOf(report.getSaleCash() + report.getSaleCard()), true);

        printEmptyLine();
    }

    private void printReturns(){
        printString( 10, true, PrintReceipt.Align.LEFT, false, "ПОВЕРНЕННЯ", true);
        printString( 8, false, PrintReceipt.Align.LEFT, false, "Кількість чеків", false);
        printString( 8, false, PrintReceipt.Align.RIGHT, false, String.valueOf(report.getCountReturnReceipts()), true);

        printString( 8, false, PrintReceipt.Align.LEFT, false, "Готівка", false);
        printString( 8, false, PrintReceipt.Align.RIGHT, false, String.valueOf(report.getReturnCash()), true);

        printString( 8, false, PrintReceipt.Align.LEFT, false, "Картка", false);
        printString( 8, false, PrintReceipt.Align.RIGHT, false, String.valueOf(report.getReturnCard()), true);

        printString( 8, false, PrintReceipt.Align.LEFT, false, "Загалом", false);
        printString( 8, false, PrintReceipt.Align.RIGHT, false, String.valueOf(report.getReturnCash() + report.getReturnCard()), true);

        printEmptyLine();
    }

    private void printMoneysMove(){
        printString( 8, false, PrintReceipt.Align.LEFT, false, "Службове внесення", false);
        printString( 8, false, PrintReceipt.Align.RIGHT, false, String.valueOf(report.getMoneyIn()), true);

        printString( 8, false, PrintReceipt.Align.LEFT, false, "Службова видача", false);
        printString( 8, false, PrintReceipt.Align.RIGHT, false, String.valueOf(report.getMoneyOut()), true);

        printEmptyLine();
    }

    private void printSafe(){
        printString( 8, false, PrintReceipt.Align.LEFT, false, "В сейфі", false);
        printString( 8, false, PrintReceipt.Align.RIGHT, false, String.valueOf(report.getSafe()), true);

        printEmptyLine();
    }

    private void printEmptyLine(){
        printString( 8, false, PrintReceipt.Align.CENTER, false, "", true);
    }

    //----------------------
    private void printString(int fontSize,
                             boolean bold,
                             PrintReceipt.Align align,
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

}
