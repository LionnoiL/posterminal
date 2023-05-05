package ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
public class InfoReport {

    @SerializedName(value = "dt")
    private String dt;
    @SerializedName(value = "fisid")
    private long fiscalId;
    @SerializedName(value = "docno")
    private String documentNumber;
    @SerializedName(value = "docno_from")
    private int documentNumberFrom;
    @SerializedName(value = "docno_to")
    private int documentNumberTo;
    @SerializedName(value = "doccode")
    private String documentCode;
    @SerializedName(value = "taxes")
    private ReportTotalTaxes[] taxes;
    @SerializedName(value = "pays")
    private ReportTotalPays[] pays;
    @SerializedName(value = "money")
    private ReportTotalMoneys[] money;
    private ReportReceiptRow receipt;
    @SerializedName(value = "isprint")
    private int isPrint;
    @SerializedName(value = "cashier")
    private String cashier;
    @SerializedName(value = "shift_link")
    private int shiftLink;
    @SerializedName(value = "safe")
    private double safe;
    private ReportShortRow[] reports;
    @SerializedName(value = "devinfo")
    private String deviceInfo;
    @SerializedName(value = "dataid")
    private int numberPacket;
    @SerializedName(value = "vacant_off_nums")
    private String vacantOfflineNumbers;
}
