package ua.gaponov.posterminal.entity.shift;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class ShiftResult {

    private long id;
    private LocalDateTime date;
    private String userGuid;
    private double summaOrderCash;
    private double summaReturnCash;
    private double summaSafe;
    private double summaOrderCard;
    private double summaReturnCard;
    private double summaCard;
    private double summaMoneyMoveIn;
    private double summaMoneyMoveOut;
    private double summaSale;
    private double summaReturn;
}
