package ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
public class PrintInfoTaxesRow {

    @SerializedName(value = "gr_code")
    private int grCode;
    @SerializedName(value = "base_sum")
    private double baseSum;
    @SerializedName(value = "tax_name")
    private String taxName;
    @SerializedName(value = "tax_percent")
    private double taxPercent;
    @SerializedName(value = "tax_sum")
    private double taxSum;
    @SerializedName(value = "ex_name")
    private String exName;
    @SerializedName(value = "ex_percent")
    private double exPercent;
    @SerializedName(value = "ex_sum")
    private double exSum;
}
