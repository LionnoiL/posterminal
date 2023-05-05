package ua.gaponov.posterminal.devices.fiscal.vchasno.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import ua.gaponov.posterminal.devices.fiscal.vchasno.enums.PayType;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class Pay {

    private PayType type;
    private double sum;
    private String currency;
    private String comment;
    private double change;
    @SerializedName(value = "paysys")
    private String paySystem;
    private String rrn;
    @SerializedName(value = "cardmask")
    private String cardMask;
    @SerializedName(value = "term_id")
    private String terminalId;
    @SerializedName(value = "bank_id")
    private String bankId;
    @SerializedName(value = "auth_code")
    private String authCode;
    private double commission;
}
