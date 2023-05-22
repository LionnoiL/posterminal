package ua.gaponov.posterminal.entity.messages;

import lombok.*;

import java.io.Serializable;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor()
@Builder
public class ExchangeMessage implements Serializable {
    private long receivedNumber;
    private long takenNumber;
}
