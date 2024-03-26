package ua.gaponov.posterminal.entity.moneymove;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.gaponov.posterminal.entity.MoveType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class MoneyMove implements Cloneable {

    private String guid = UUID.randomUUID().toString();
    private long moneyMoveNumber;
    private LocalDateTime date;
    @JsonIgnore
    private boolean upload;
    private double documentSum;
    private MoveType moveType;
    private String comment;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
