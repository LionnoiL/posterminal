package ua.gaponov.posterminal.devices.fiscal.vchasno.entity.info;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.ToString;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
public class ReportShortRow {

    @SerializedName(value = "dt")
    private String dt;
    @SerializedName(value = "docno")
    private String documentNumber;
    @SerializedName(value = "doccode")
    private String documentCode;
    @SerializedName(value = "docsum")
    private double documentSum;
}
