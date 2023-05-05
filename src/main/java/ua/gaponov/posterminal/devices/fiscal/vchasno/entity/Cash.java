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
public class Cash {

    private PayType type;
    private double sum;
    @SerializedName(value = "comment_up")
    private String commentUp;
    @SerializedName(value = "comment_down")
    private String commentDown;
}
