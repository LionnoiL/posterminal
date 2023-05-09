package ua.gaponov.posterminal.server;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Andriy Gaponov
 */
@Getter
@AllArgsConstructor(staticName = "of")
public class ResponseEntity {

    private final String content;
    private final Integer status;
}
