package ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
public class InfoOpenShift {

    @SerializedName(value = "fisid")
    private long fiscalId;
    @SerializedName(value = "dt")
    private String date;
    @SerializedName(value = "doccode")
    private String documentCode;
    @SerializedName(value = "dataid")
    private int numberPacket;
    @SerializedName(value = "shift_link")
    private int shiftLink;
    @SerializedName(value = "cashier")
    private String cashier;
    @SerializedName(value = "devinfo")
    private String deviceInfo;
    @SerializedName(value = "vacant_off_nums")
    private int vacantOfflineNumbers;
}
