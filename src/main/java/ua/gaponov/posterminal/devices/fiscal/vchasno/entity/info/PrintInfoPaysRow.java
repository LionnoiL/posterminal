package ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
public class PrintInfoPaysRow {

    private String type;
    private double sum;
    private String info;
    private String comment;
    @SerializedName(value = "paysys")
    private String paySystem;
    private String rrn;
    @SerializedName(value = "cardmask")
    private String cardMask;
    private String currency;
    @SerializedName(value = "term_id")
    private String terminalId;
    @SerializedName(value = "bank_id")
    private String bankId;
    @SerializedName(value = "auth_code")
    private String authCode;
}
