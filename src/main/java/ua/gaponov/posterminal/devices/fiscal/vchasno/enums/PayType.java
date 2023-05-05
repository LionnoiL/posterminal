package ua.gaponov.posterminal.devices.fiscal.vchasno.enums;

import com.google.gson.annotations.SerializedName;

/**
 * @author Andriy Gaponov
 */
public enum PayType {
    @SerializedName("0")
    CASH,
    @SerializedName("1")
    CASHLESS,
    @SerializedName("2")
    CARD,
    @SerializedName("3")
    PRE_PAY,
    @SerializedName("4")
    POST_PAY,
    @SerializedName("5")
    CREDIT,
    @SerializedName("6")
    CERTIFICATE
}
