package ua.gaponov.posterminal.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.dataexchange.upload.ExchangeUpload;
import ua.gaponov.posterminal.entity.DatePeriod;
import ua.gaponov.posterminal.entity.options.OptionsValue;
import ua.gaponov.posterminal.entity.options.OptionsValueService;
import ua.gaponov.posterminal.entity.orders.DeletedOrderItem;
import ua.gaponov.posterminal.entity.orders.OrderService;
import ua.gaponov.posterminal.server.commands.CommandResponse;
import ua.gaponov.posterminal.server.commands.PropertyCommand;
import ua.gaponov.posterminal.server.commands.TerminalCommandRequest;
import ua.gaponov.posterminal.server.exception.ServerInternalErrorException;
import ua.gaponov.posterminal.utils.PropertiesUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import static ua.gaponov.posterminal.utils.JsonUtils.GSON;

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
        Runtime runtime = Runtime.getRuntime();
        long totalHeapSize = runtime.totalMemory()  / (1024 * 1024);
        long freeHeapSize = runtime.freeMemory()  / (1024 * 1024);
        long usedHeapSize = totalHeapSize - freeHeapSize;

        String res = """
                {
                "name":"posterminal",
                "shop_name": "%s",
                "cash_register_name": "%s",
                "arm_id": %d,
                "last_update": "%s",
                "total_heap_size": "%d",
                "free_heap_size": "%d",
                "used_heap_size": "%d"
                }
                """;
        return ResponseEntity.of(
                String.format(
                        res,
                        AppProperties.getShopName(),
                        AppProperties.getCashRegisterName(),
                        AppProperties.getArmId(),
                        lastUpdate.getValue(),
                        totalHeapSize,
                        freeHeapSize,
                        usedHeapSize
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
                    String result = handleCommand(new String(bytes));
                    return ResponseEntity.of(result, status, CONTENT_TYPE_JSON);
                default:
                    return ResponseEntity.of("", 404, "");
            }
        } catch (Exception e) {
            throw new ServerInternalErrorException("can't read request");
        }
    }

    private String handleCommand(String command) {
        TerminalCommandRequest terminalCommandRequest = GSON.fromJson(command, TerminalCommandRequest.class);
        LOG.debug(command);

        switch (terminalCommandRequest.getCommand()) {
            case EXPORT:
                ExchangeUpload.upload();
                return GSON.toJson(CommandResponse.of(terminalCommandRequest.getCommand(), "", ""));
            case LOG:
                ExchangeUpload.uploadLog();
                return GSON.toJson(CommandResponse.of(terminalCommandRequest.getCommand(), "", ""));
            case SET_PROPERTY:
                PropertiesUtils.setPropertiesValues(terminalCommandRequest.getRequestString());
                return GSON.toJson(CommandResponse.of(terminalCommandRequest.getCommand(), "", ""));
            case GET_PROPERTY:
                PropertyCommand propertiesValues = PropertiesUtils.getPropertiesValues();
                return GSON.toJson(CommandResponse.of(terminalCommandRequest.getCommand(),
                        GSON.toJson(propertiesValues), "")
                );
            case GET_DELETED_ITEMS:
                DatePeriod datePeriod = GSON.fromJson(terminalCommandRequest.getRequestString(), DatePeriod.class);
                List<DeletedOrderItem> deletedProducts = OrderService.getDeletedProducts(datePeriod);
                return GSON.toJson(CommandResponse.of(terminalCommandRequest.getCommand(),
                        GSON.toJson(deletedProducts), "")
                );
            default:
                return GSON.toJson(CommandResponse.of(terminalCommandRequest.getCommand(), "", "command not found"));
        }
    }
}
