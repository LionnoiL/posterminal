package ua.gaponov.posterminal.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.server.exception.ServerInternalErrorException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.time.LocalDateTime;

/**
 * @author Andriy Gaponov
 */
public class ServerHandler implements HttpHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ServerHandler.class);

    private static void sendResponse(HttpExchange exchange, ResponseEntity responseEntity) throws IOException {
        exchange.sendResponseHeaders(responseEntity.getStatus(), responseEntity.getContent().length());
        OutputStream os = exchange.getResponseBody();
        os.write(responseEntity.getContent().getBytes());
        os.close();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        URI endpoint = exchange.getRequestURI();
        LOG.debug("consumed request:{}, endpoint:{}", requestMethod, endpoint);
        ResponseEntity responseEntity = null;
        try {
            switch (requestMethod) {
                case "GET":
                    responseEntity = handleGET(exchange, endpoint);
                    break;
                case "POST":
                    responseEntity = handlePOST(exchange, endpoint);
                    break;
                default:
                    throw new ServerInternalErrorException("unsupported http method");
            }
        } catch (Exception e) {
            if (e instanceof ServerInternalErrorException) {
                int httpStatus = ((ServerInternalErrorException) e).getHttpCode();
                String responseBody = e.getMessage();
                responseEntity = ResponseEntity.of(responseBody, httpStatus);
            } else {
                final int status = 500;
                responseEntity = ResponseEntity.of("", status);
            }
        } finally {
            sendResponse(exchange, responseEntity);
        }

    }

    private ResponseEntity handleGET(HttpExchange exchange, URI endpoint) {
        String path = endpoint.getPath();
        switch (path) {
            case "/last-update":
                return getLastUpdate();
            case "/echo":
                return getApplicationEcho();
            default:
                return ResponseEntity.of("", 404);
        }

    }

    private ResponseEntity getLastUpdate() {
        final int status = 200;
        return ResponseEntity.of(LocalDateTime.now().toString(), status);
    }

    private ResponseEntity getApplicationEcho() {
        final int status = 200;
        return ResponseEntity.of("posterminal", status);
    }

    private ResponseEntity handlePOST(HttpExchange exchange, URI endpoint) {
        try {
            byte[] bytes = exchange.getRequestBody().readAllBytes();
            LOG.info(new String(bytes));
        } catch (Exception e) {
            throw new ServerInternalErrorException("can't read request");
        }
        final int status = 201;
        return ResponseEntity.of("post", status);
    }
}
