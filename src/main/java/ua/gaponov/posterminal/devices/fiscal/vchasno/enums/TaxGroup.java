package ua.gaponov.posterminal.devices.fiscal.vchasno.enums;

import com.google.gson.annotations.SerializedName;

/**
 * @author Andriy Gaponov
 */
public enum TaxGroup {
    @SerializedName("1")
    VAT_20,
    @SerializedName("2")
    NO_VAT,
    @SerializedName("3")
    VAT_20_5,
    @SerializedName("4")
    VAT_7,
    @SerializedName("5")
    VAT_0,
    @SerializedName("6")
    NO_VAT_5,
    @SerializedName("7")
    NOT_SUBJECT_TO_VAT,
    @SerializedName("8")
    VAT_20_75,
    @SerializedName("9")
    VAT_14
}
