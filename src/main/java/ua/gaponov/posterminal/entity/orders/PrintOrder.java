package ua.gaponov.posterminal.entity.orders;

import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.devices.printer.Printer;
import ua.gaponov.posterminal.entity.DocumentTypes;
import ua.gaponov.posterminal.entity.organization.Organization;
import ua.gaponov.posterminal.utils.DateUtils;
import ua.gaponov.posterminal.utils.RoundUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Andriy Gaponov
 */
public class PrintOrder implements Printable {

    private final Order order;
    private Printer printer = new Printer(130, 1000, 0, 0, this);

    public PrintOrder(Order order) {
        this.order = order;
        printer.setCurrentLine(-120 + -30 * order.getDetails().size());
        try {
            printer.print();
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(null, "Printing Failed, Error: " + ex.toString());
        }
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        printer.setG2d((Graphics2D) graphics);

        printTitle();
        printProducts();
        printOrganizations();
        printTotals();
        printPays();
        printCardInfo();
        printFooter();

        return PAGE_EXISTS;
    }

    private void printTitle() {
        String documentType = "Продаж";
        if (Objects.equals(DocumentTypes.RETURN, order.getDocumentType())) {
            documentType = "Повернення";
        }
        printer.printCenter(AppProperties.getShopName(), 10, true, false);
        printer.printCenter(AppProperties.getShopAddress(), 8, true, true);
        printer.printTwoLines(DateUtils.getDateTimeNow(), documentType, 6, false);
        printer.printCenter("Товарний чек №" + order.getOrderNumber(), 8);
        printer.printCenter(AppProperties.getCashRegisterName(), 8);
        printer.printHorizontalLine();
    }

    private void printOrganizations() {
        Map<Organization, Double> totalsByOrganizations = order.getTotalsByOrganizations();
        for (Map.Entry<Organization, Double> organizationDoubleEntry : totalsByOrganizations.entrySet()) {
            String organizationName = "";
            if (Objects.nonNull(organizationDoubleEntry.getKey())) {
                organizationName = organizationDoubleEntry.getKey().getName();
            }
            printer.printTwoLines(organizationName, String.valueOf(RoundUtils.round(organizationDoubleEntry.getValue())), 6);
        }
        printer.printHorizontalLine();
    }

    private void printTotals() {
        printer.printTwoLines("ПІДСУМОК",
                RoundUtils.round(order.getDocumentSumWithoutDiscount()) + " " + AppProperties.getCurrency(),
                8);
    }

    private void printPays() {
        printer.printTwoLines("ЗНИЖКА СКЛАЛА",
                RoundUtils.round(order.getDiscountSum()) + " " + AppProperties.getCurrency(),
                8);

        String sign = "+";
        if (order.getRoundSum() > 0) {
            sign = "-";
        }
        printer.printTwoLines("ОКРУГЛЕННЯ",
                sign + Math.abs(RoundUtils.round(order.getRoundSum())) + " " + AppProperties.getCurrency(),
                8);

        String documentType = "ДО СПЛАТИ";
        if (Objects.equals(DocumentTypes.RETURN, order.getDocumentType())) {
            documentType = "ДО ПОВЕРНЕННЯ";
        }
        printer.printTwoLines(documentType, RoundUtils.round(order.getToPaySum()) + " " + AppProperties.getCurrency(),
                10);
        printer.printTwoLines("СПЛАЧЕНО",
                order.getPaySum() + " " + AppProperties.getCurrency(),
                8);
        printer.printTwoLines("РЕШТА",
                order.getPaySum() - RoundUtils.round(order.getToPaySum()) + " " + AppProperties.getCurrency(),
                8);
    }

    private void printCardInfo() {
        if (order.getCard() != null) {
            printer.printCenter("Поточна знижка по картці " + order.getCard().getDiscount() + "%",
                    8, true, false);
        }
    }

    private void printFooter() {
        printer.printCenter("Дякуємо за покупку", 10, true, false);
    }

    private void printProducts() {
        List<OrderDetail> details = order.getDetails();
        for (OrderDetail detail : details) {
            printer.printLeft(detail.getProduct().getName().toUpperCase(), 8, true);
            printer.printRight(detail.getQty()
                    + "(" + detail.getProduct().getUnitName() + ")x"
                    + detail.getPrice()
                    + "="
                    + RoundUtils.round(detail.getSummaWithoutDiscount()), 8);
        }
        printer.printHorizontalLine();
    }

    public enum Align {
        LEFT, CENTER, RIGHT
    }
}
