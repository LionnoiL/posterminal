package ua.gaponov.posterminal.documents.moneymove;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.gaponov.posterminal.documents.MoveType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MoneyMove {

    private String guid = UUID.randomUUID().toString();
    private long moneyMoveNumber;
    private LocalDateTime date;
    private transient boolean upload;
    private double documentSum;
    private MoveType moveType;
    private String comment;
}
