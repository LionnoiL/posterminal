package ua.gaponov.posterminal.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.dataexchange.upload.ExchangeUpload;
import ua.gaponov.posterminal.entity.options.OptionsValue;
import ua.gaponov.posterminal.entity.options.OptionsValueService;
import ua.gaponov.posterminal.server.exception.ServerInternalErrorException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Andriy Gaponov
 */
public class ServerHandler implements HttpHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ServerHandler.class);
    private static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";

    private static void sendResponse(HttpExchange exchange, ResponseEntity responseEntity) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", responseEntity.getContentType());
        exchange.sendResponseHeaders(responseEntity.getStatus(), 0);
        OutputStream os = exchange.getResponseBody();
        os.write(responseEntity.getContent().getBytes(StandardCharsets.UTF_8));
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
                responseEntity = ResponseEntity.of(responseBody, httpStatus, "");
            } else {
                final int status = 500;
                responseEntity = ResponseEntity.of("", status, "");
            }
        } finally {
            if (Objects.nonNull(responseEntity)) {
                sendResponse(exchange, responseEntity);
            }
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
                return ResponseEntity.of("", 404, "");
        }

    }

    private ResponseEntity getLastUpdate() {
        final int status = 200;
        OptionsValue lastUpdate = OptionsValueService.getOptions("last_update");
        return ResponseEntity.of(lastUpdate.getValue(), status, "");
    }

    private ResponseEntity getApplicationEcho() {
        final int status = 200;
        OptionsValue lastUpdate = OptionsValueService.getOptions("last_update");
        String res = """
                {
                "name":"posterminal",
                "shop_name": "%s",
                "cash_register_name": "%s",
                "arm_id": %d,
                "last_update": "%s"
                }
                """;
        return ResponseEntity.of(
                String.format(
                        res,
                        AppProperties.getShopName(),
                        AppProperties.getCashRegisterName(),
                        AppProperties.getArmId(),
                        lastUpdate.getValue()
                )
                , status, CONTENT_TYPE_JSON);
    }

    private ResponseEntity handlePOST(HttpExchange exchange, URI endpoint) {
        final int status = 201;
        try {
            byte[] bytes = exchange.getRequestBody().readAllBytes();

            String path = endpoint.getPath();
            switch (path) {
                case "/command":
                    handleCommand(new String(bytes));
                    return ResponseEntity.of("", status, CONTENT_TYPE_JSON);
                default:
                    return ResponseEntity.of("", 404, "");
            }
        } catch (Exception e) {
            throw new ServerInternalErrorException("can't read request");
        }
    }

    private void handleCommand(String command) {
        switch (command) {
            case "EXPORT":
                ExchangeUpload.upload();
                break;
            default:
                //NOP
        }
        LOG.info(command);
    }
}
