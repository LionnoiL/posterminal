package ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
public class ReportTotalPays {

    private int type;
    private String name;
    @SerializedName(value = "sum_p")
    private double sumSale;
    @SerializedName(value = "sum_m")
    private double sumReturn;
    @SerializedName(value = "round_pu")
    private double roundPu;
    @SerializedName(value = "round_pd")
    private double roundPd;
    @SerializedName(value = "round_mu")
    private double roundMu;
    @SerializedName(value = "round_md")
    private double roundMd;
}
