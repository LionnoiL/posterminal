package ua.gaponov.posterminal.prostopay;

import lombok.Getter;

/**
 * @author Andriy Gaponov
 */
public class ProstoPayProductNotFoundException extends RuntimeException {
    @Getter
    private final int httpCode = 500;

    public ProstoPayProductNotFoundException(String message) {
        super(message);
    }

    public ProstoPayProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
