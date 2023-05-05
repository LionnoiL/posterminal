package ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
public class PrintInfoGoodsRow {

    private String name;
    @SerializedName(value = "code1")
    private String barcode;
    @SerializedName(value = "code2")
    private String uktzed;
    @SerializedName(value = "code3")
    private String dkppCode;
    @SerializedName(value = "code_a")
    private String exciseCode;
    @SerializedName(value = "code_aa")
    private String[] exciseCodes;
    private double cnt;
    private double price;
    private double cost;
    private double disc;
    private String taxlit;
    private String comment;
}
