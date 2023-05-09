package ua.gaponov.posterminal.devices.printer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.documents.orders.PrintOrder;
import ua.gaponov.posterminal.utils.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;

/**
 * @author Andriy Gaponov
 */
public class Printer {

    private static final Logger LOG = LoggerFactory.getLogger(Printer.class);

    private final int MARGIN;
    private final int PAPER_WIDTH;
    private final int PAPER_HEIGHT;
    private final int FONT_MARGIN = 10;
    private PrinterJob printerJob;
    private PageFormat pageFormat;
    private Paper paper;
    private int currentLine;
    private Graphics2D g2d;

    public Printer(int paper_width, int paper_height, int margin, int startLine, Printable printable) {
        MARGIN = margin;
        PAPER_WIDTH = paper_width;
        PAPER_HEIGHT = paper_height;
        currentLine = startLine;

        printerJob = PrinterJob.getPrinterJob();
        pageFormat = printerJob.defaultPage();

        paper = new Paper();
        paper.setImageableArea(MARGIN, MARGIN, PAPER_WIDTH, PAPER_HEIGHT);
        pageFormat.setPaper(paper);
        pageFormat.setOrientation(PageFormat.PORTRAIT);
        printerJob.setPrintable(printable, pageFormat);
        printerJob.setCopies(1);
    }

    public void print() throws PrinterException {
        printerJob.print();
    }

    public void setG2d(Graphics2D g2d) {
        this.g2d = g2d;
    }

    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
    }

    private void printString(int fontSize,
                             boolean bold,
                             PrintOrder.Align align,
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

    public void printHorizontalLine() {
        g2d.drawLine(0, currentLine, PAPER_WIDTH, currentLine);
        currentLine = currentLine + FONT_MARGIN;
    }

    public void printEmptyString(){
        printLeft("", 8);
    }

    public void printCenter(String text, int fontSize, boolean bold, boolean wrap){
        printString(fontSize, bold, PrintOrder.Align.CENTER, wrap,text, true);
    }

    public void printCenter(String text, int fontSize, boolean wrap){
        printString(fontSize, false, PrintOrder.Align.CENTER, wrap,text, true);
    }

    public void printCenter(String text, int fontSize){
        printString(fontSize, false, PrintOrder.Align.CENTER, false,text, true);
    }

    public void printLeft(String text, int fontSize, boolean bold, boolean wrap){
        printString(fontSize, bold, PrintOrder.Align.LEFT, wrap,text, true);
    }

    public void printLeft(String text, int fontSize, boolean wrap){
        printString(fontSize, false, PrintOrder.Align.LEFT, wrap,text, true);
    }

    public void printLeft(String text, int fontSize){
        printString(fontSize, false, PrintOrder.Align.LEFT, false,text, true);
    }

    public void printRight(String text, int fontSize, boolean bold, boolean wrap){
        printString(fontSize, bold, PrintOrder.Align.RIGHT, wrap, text, true);
    }

    public void printRight(String text, int fontSize, boolean wrap){
        printString(fontSize, false, PrintOrder.Align.RIGHT, wrap,text, true);
    }

    public void printRight(String text, int fontSize){
        printString(fontSize, false, PrintOrder.Align.RIGHT, false,text, true);
    }

    public void printTwoLines(String textLeft, String textRight, int fontSizeLeft, int fontSizeRight,
                              boolean boldLeft, boolean boldRight){
        printString(fontSizeLeft, boldLeft, PrintOrder.Align.LEFT, false,textLeft, false);
        printString(fontSizeRight, boldRight, PrintOrder.Align.RIGHT, false,textRight, true);
    }

    public void printTwoLines(String textLeft, String textRight, int fontSize,
                              boolean boldLeft, boolean boldRight){
        printString(fontSize, boldLeft, PrintOrder.Align.LEFT, false,textLeft, false);
        printString(fontSize, boldRight, PrintOrder.Align.RIGHT, false,textRight, true);
    }

    public void printTwoLines(String textLeft, String textRight, int fontSize, boolean bold){
        printString(fontSize, bold, PrintOrder.Align.LEFT, false,textLeft, false);
        printString(fontSize, bold, PrintOrder.Align.RIGHT, false,textRight, true);
    }

    public void printTwoLines(String textLeft, String textRight, int fontSize){
        printString(fontSize, false, PrintOrder.Align.LEFT, false,textLeft, false);
        printString(fontSize, false, PrintOrder.Align.RIGHT, false,textRight, true);
    }

    public void printImage(BufferedImage image){
        g2d.drawImage(image, 1, currentLine, null);
    }

    public void printImage(BufferedImage image, boolean scaleByWidthPage){
        if (scaleByWidthPage){
            int scale = image.getWidth() / PAPER_WIDTH;
            g2d.drawImage(image, 1, currentLine, PAPER_WIDTH, image.getHeight() / scale,null);
        } else {
            g2d.drawImage(image, 1, currentLine, null);
        }
    }
}
