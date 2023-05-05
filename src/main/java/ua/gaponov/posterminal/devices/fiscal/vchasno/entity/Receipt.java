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
public class Receipt {

    private double sum;
    private double round;
    @SerializedName(value = "comment_up")
    private String commentUp;
    @SerializedName(value = "comment_down")
    private String commentDown;
    private Row[] rows;
    private Pay[] pays;
}
