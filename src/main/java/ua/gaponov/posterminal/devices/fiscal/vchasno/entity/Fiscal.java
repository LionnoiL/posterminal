package ua.gaponov.posterminal.devices.fiscal.vchasno.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import ua.gaponov.posterminal.devices.fiscal.vchasno.enums.Task;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Fiscal {

    @SerializedName(value = "fisid")
    private String fiscalId;
    private Task task;
    private String cashier;
    private Receipt receipt;
    private Cash cash;
    @SerializedName(value = "n_from")
    private int numberFrom;
    @SerializedName(value = "n_to")
    private int numberTo;
    @SerializedName(value = "dt_from")
    private String dateFrom;
    @SerializedName(value = "dt_to")
    private String dateTo;
    private NoFiscalInfo[] lines;

    public static Fiscal status(){
        Fiscal fiscal = new Fiscal();
        fiscal.setTask(Task.STATUS);
        return fiscal;
    }

    public static Fiscal openShift(){
        Fiscal fiscal = new Fiscal();
        fiscal.setTask(Task.OPEN_SHIFT);
        return fiscal;
    }

    public static Fiscal xReport(){
        Fiscal fiscal = new Fiscal();
        fiscal.setTask(Task.X_REPORT);
        return fiscal;
    }

    public static Fiscal zReport(){
        Fiscal fiscal = new Fiscal();
        fiscal.setTask(Task.Z_REPORT);
        return fiscal;
    }

    public static Fiscal salesReceipt(){
        Fiscal fiscal = new Fiscal();
        fiscal.setTask(Task.SALES_RECEIPT);
        return fiscal;
    }
}
