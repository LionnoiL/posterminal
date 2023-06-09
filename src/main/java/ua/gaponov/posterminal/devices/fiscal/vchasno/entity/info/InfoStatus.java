package ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
public class InfoStatus {

    @SerializedName(value = "fisid")
    private long fiscalId;
    @SerializedName(value = "isFis")
    private int isFiscal;
    @SerializedName(value = "shift_status")
    private int shiftStatus;
    @SerializedName(value = "shift_dt")
    private String shiftDate;
    @SerializedName(value = "online_status")
    private int onlineStatus;
    @SerializedName(value = "sign_status")
    private int signStatus;
    @SerializedName(value = "safe")
    private double safe;
}
