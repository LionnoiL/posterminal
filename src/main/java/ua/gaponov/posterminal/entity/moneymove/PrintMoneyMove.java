package ua.gaponov.posterminal.entity.moneymove;

import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.devices.printer.Printer;
import ua.gaponov.posterminal.entity.MoveType;
import ua.gaponov.posterminal.utils.DateUtils;
import ua.gaponov.posterminal.utils.RoundUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * @author Andriy Gaponov
 */
public class PrintMoneyMove implements Printable {

    private final MoneyMove moneyMove;
    private Printer printer = new Printer(130, 1000, 0, 0, this);

    public PrintMoneyMove(MoneyMove moneyMove) {
        this.moneyMove = moneyMove;
        //printer.setCurrentLine(-120);
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
        printPays();

        return PAGE_EXISTS;
    }

    private void printTitle() {
        String moveType = "Службова видача";
        if (moneyMove.getMoveType().equals(MoveType.MOVE_IN)) {
            moveType = "Службове внесення";
        }
        printer.printCenter(AppProperties.getShopName(), 10, true, false);
        printer.printCenter(AppProperties.getShopAddress(), 8, true, true);
        printer.printCenter(DateUtils.getDateTimeNow(),  6, false);
        printer.printCenter(moveType + " №" + moneyMove.getMoneyMoveNumber(), 8);
        printer.printCenter(AppProperties.getCashRegisterName(), 8);
        printer.printHorizontalLine();
    }


    private void printPays() {
        printer.printTwoLines("Сума",
                RoundUtils.round(moneyMove.getDocumentSum()) + " " + AppProperties.getCurrency(),
                8);

        printer.printTwoLines("Коментар", moneyMove.getComment(), 10);
    }

    public enum Align {
        LEFT, CENTER, RIGHT
    }
}
