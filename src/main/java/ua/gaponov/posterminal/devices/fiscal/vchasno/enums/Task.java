package ua.gaponov.posterminal.devices.fiscal.vchasno.enums;

import com.google.gson.annotations.SerializedName;

/**
 * @author Andriy Gaponov
 */
public enum Task {

    @SerializedName("0")
    OPEN_SHIFT,
    @SerializedName("1")
    SALES_RECEIPT,
    @SerializedName("2")
    RETURN_RECEIPT,
    @SerializedName("3")
    DEPOSITING_MONEY,
    @SerializedName("4")
    WITHDRAWAL_OF_MONEY,
    @SerializedName("5")
    NON_FISCAL_SERVICE_REPORT,
    @SerializedName("10")
    X_REPORT,
    @SerializedName("11")
    Z_REPORT,
    @SerializedName("12")
    SUMMARY_REPORT_BY_NUMBERS,
    @SerializedName("13")
    SUMMARY_REPORT_BY_DATES,
    @SerializedName("14")
    CASH_RECEIPT,
    @SerializedName("18")
    STATUS,
    @SerializedName("20")
    FISCAL_NUMBER,
    @SerializedName("21")
    LAST_NUMBERS_RECEIPT,
    @SerializedName("22")
    REPEAT_LAST_RECEIPT
}
