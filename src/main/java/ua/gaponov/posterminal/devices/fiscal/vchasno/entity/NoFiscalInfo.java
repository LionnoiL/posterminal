package ua.gaponov.posterminal.devices.fiscal.vchasno.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * @author Andriy Gaponov
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class NoFiscalInfo {

    @SerializedName(value = "t")
    private String text;
    @SerializedName(value = "qr_type")
    private int qrType;
}
