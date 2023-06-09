package ua.gaponov.posterminal.entity.messages;

import lombok.*;

import java.io.Serializable;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
public class ExchangeMessage implements Serializable {
    private long receivedNumber;
    private long takenNumber;
}
