package ua.gaponov.posterminal.devices.fiscal.vchasno.enums;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

/**
 * @author Andriy Gaponov
 */
@Getter
public enum TaxGroup {
    @SerializedName("1")
    VAT_20(1),
    @SerializedName("2")
    NO_VAT(2),
    @SerializedName("3")
    VAT_20_5(3),
    @SerializedName("4")
    VAT_7(4),
    @SerializedName("5")
    VAT_0(5),
    @SerializedName("6")
    NO_VAT_5(6),
    @SerializedName("7")
    NOT_SUBJECT_TO_VAT(7),
    @SerializedName("8")
    VAT_20_75(8),
    @SerializedName("9")
    VAT_14(9);

    private final int value;


    TaxGroup(int value) {
        this.value = value;
    }
}
