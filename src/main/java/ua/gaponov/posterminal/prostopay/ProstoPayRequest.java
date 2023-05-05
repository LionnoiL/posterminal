package ua.gaponov.posterminal.prostopay;

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
public class ProstoPayRequest {

    private int pos;
    private int till;
    private int number;
    @SerializedName(value = "created-at")
    private int createdAt = 0;
    private int ttl = 1;
    @SerializedName(value = "ttl-type")
    private int ttlType = 0;
    private int amount;
    @SerializedName(value = "amount-base")
    private int amountBase = 3;
    @SerializedName(value = "plu-from")
    private int pluFrom;
    @SerializedName(value = "plu-to")
    private int pluTo = 0;
}
