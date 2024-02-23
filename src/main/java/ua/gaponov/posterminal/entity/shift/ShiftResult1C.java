package ua.gaponov.posterminal.entity.shift;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ShiftResult1C {

    private Long id;
    private String docDate;
    private double cashStart;
    private double cashEnd;
    private double summaSale;
    private double summaReturn;
    private double saleCash;
    private double saleCard;
    private double moneyIn;
    private double moneyOut;
}
