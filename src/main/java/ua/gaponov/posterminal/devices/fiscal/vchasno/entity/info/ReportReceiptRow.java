package ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
public class ReportReceiptRow {

    @SerializedName(value = "count_p")
    private int countReceiptSale;
    @SerializedName(value = "count_m")
    private int countReceiptReturn;
}
