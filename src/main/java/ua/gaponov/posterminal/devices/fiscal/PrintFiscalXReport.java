package ua.gaponov.posterminal.devices.fiscal;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import javax.swing.JOptionPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.devices.printer.Printer;
import ua.gaponov.posterminal.utils.DateUtils;

/**
 * @author Andriy Gaponov
 */
public class PrintFiscalXReport implements Printable {

    private static final Logger LOG = LoggerFactory.getLogger(PrintFiscalXReport.class);
    private Printer printer = new Printer(130, 1000, 0, 0, this);
    private XReportDto report;

    public PrintFiscalXReport(XReportDto report) {
        this.report = report;

        printer.setCurrentLine(-120);

        try {
            printer.print();
        } catch (PrinterException ex) {
            LOG.error("Printing x-report failed", ex);
            JOptionPane.showMessageDialog(null, "Printing Failed, Error: " + ex.toString());
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
        throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        printer.setG2d((Graphics2D) graphics);

        printTitle();
        printSales();
        printReturns();
        printMoneysMove();
        printSafe();
        printFooter();

        return PAGE_EXISTS;
    }

    private void printTitle() {
        printer.printCenter(AppProperties.shopName, 10, false, true);
        printer.printCenter("X-звіт", 12, false, true);
        printer.printEmptyString();
    }

    private void printFooter() {
        printer.printCenter(DateUtils.getDateTimeNow(), 8);
        printer.printTwoLines(AppProperties.cashRegisterName, AppProperties.fiscalName, 8);
    }

    private void printSales() {
        printer.printLeft("ПРОДАЖ", 10, false, true);
        printer.printTwoLines("Кількість чеків", String.valueOf(report.getCountSaleReceipts()), 8);
        printer.printTwoLines("Готівка", String.valueOf(report.getSaleCash()), 8);
        printer.printTwoLines("Картка", String.valueOf(report.getSaleCard()), 8);
        printer.printTwoLines("Загалом",
            String.valueOf(report.getSaleCash() + report.getSaleCard()), 8);

        printer.printEmptyString();
    }

    private void printReturns() {
        printer.printLeft("ПОВЕРНЕННЯ", 10, false, true);
        printer.printTwoLines("Кількість чеків", String.valueOf(report.getCountReturnReceipts()),
            8);
        printer.printTwoLines("Готівка", String.valueOf(report.getReturnCash()), 8);
        printer.printTwoLines("Картка", String.valueOf(report.getReturnCard()), 8);
        printer.printTwoLines("Загалом",
            String.valueOf(report.getReturnCash() + report.getReturnCard()), 8);

        printer.printEmptyString();
    }

    private void printMoneysMove() {
        printer.printTwoLines("Службове внесення", String.valueOf(report.getMoneyIn()), 8);
        printer.printTwoLines("Службова видача", String.valueOf(report.getMoneyOut()), 8);

        printer.printEmptyString();
    }

    private void printSafe() {
        printer.printTwoLines("В сейфі", String.valueOf(report.getSafe()), 8);

        printer.printEmptyString();
    }
}
