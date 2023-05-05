package ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
public class InfoDocument {

    @SerializedName(value = "fisid")
    private String fiscalId;
    @SerializedName(value = "dt")
    private String date;
    @SerializedName(value = "docno")
    private String documentNumber;
    @SerializedName(value = "doccode")
    private String documentCode;
    @SerializedName(value = "qr")
    private String qrCode;
    @SerializedName(value = "cancelid")
    private String cancelId;
    @SerializedName(value = "isprint")
    private int isPrint;
    @SerializedName(value = "printinfo")
    private PrintInfo printInfo;
    @SerializedName(value = "cashier")
    private String cashier;
    @SerializedName(value = "safe")
    private double safe;
    @SerializedName(value = "shift_link")
    private int shiftLink;
    @SerializedName(value = "devinfo")
    private String deviceInfo;
    @SerializedName(value = "dataid")
    private int numberPacket;
    @SerializedName(value = "vacant_off_nums")
    private String vacantOfflineNumbers;
}
