package ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
public class PrintInfo {

    private String name;
    @SerializedName(value = "shopname")
    private String shopName;
    @SerializedName(value = "shopad")
    private String shopAddress;
    @SerializedName(value = "vat_code")
    private String varCode;
    @SerializedName(value = "fis_code")
    private String fisCode;
    @SerializedName(value = "comment_up")
    private String commentUp;
    @SerializedName(value = "comment_down")
    private String commentDown;
    private PrintInfoGoodsRow[] goods;
    @SerializedName(value = "sum_0")
    private double sumWithoutDiscounts;
    @SerializedName(value = "sum_disc")
    private double sumDiscounts;
    @SerializedName(value = "sum_receipt")
    private double sumReceipt;
    @SerializedName(value = "round")
    private double sumRound;
    @SerializedName(value = "sum_topay")
    private double sumToPay;
    private PrintInfoPaysRow[] pays;
    private PrintInfoTaxesRow[] taxes;
    @SerializedName(value = "fisn")
    private String fiscaleNumber;
    @SerializedName(value = "dt")
    private String dt;
    @SerializedName(value = "qr")
    private String qrCode;
    @SerializedName(value = "isOffline")
    private boolean offline;
    @SerializedName(value = "mac")
    private String mac;
    @SerializedName(value = "fisid")
    private Long fisId;
    @SerializedName(value = "manuf")
    private String manufacture;
    @SerializedName(value = "cashier")
    private String cashier;
}
