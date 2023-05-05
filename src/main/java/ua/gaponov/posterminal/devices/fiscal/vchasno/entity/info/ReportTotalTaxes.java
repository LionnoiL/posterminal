package ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
public class ReportTotalTaxes {

    @SerializedName(value = "gr_code")
    private int grCode;
    @SerializedName(value = "base_sum_p")
    private double baseSumP;
    @SerializedName(value = "base_sum_m")
    private double baseSumM;
    @SerializedName(value = "tax_name")
    private String taxName;
    @SerializedName(value = "tax_percent")
    private double taxPercent;
    @SerializedName(value = "tax_sum_p")
    private double taxSumP;
    @SerializedName(value = "tax_sum_m")
    private double taxSumM;
    @SerializedName(value = "ex_name")
    private String exName;
    @SerializedName(value = "ex_percent")
    private String exPercent;
    @SerializedName(value = "ex_sum_p")
    private String exSumP;
    @SerializedName(value = "ex_sum_m")
    private String exSumM;
}
