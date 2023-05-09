package ua.gaponov.posterminal.server.exception;

import lombok.Getter;

/**
 * @author Andriy Gaponov
 */
public class ServerInternalErrorException extends RuntimeException {
    @Getter
    private final int httpCode = 500;

    public ServerInternalErrorException(String message) {
        super(message);
    }

    public ServerInternalErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
