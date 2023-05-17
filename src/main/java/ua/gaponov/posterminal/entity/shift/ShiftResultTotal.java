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
public class ShiftResultTotal {

    private double summaOrderCash;
    private double summaReturnCash;
    private double summaOrderCard;
    private double summaReturnCard;
    private double summaMoneyMoveIn;
    private double summaMoneyMoveOut;
    private double summaSale;
    private double summaReturn;
}
