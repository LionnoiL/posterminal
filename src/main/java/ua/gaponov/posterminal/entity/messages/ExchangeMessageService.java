package ua.gaponov.posterminal.entity.messages;

import ua.gaponov.posterminal.database.DatabaseRequest;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andriy Gaponov
 */
public class ExchangeMessageService {

    private static final SqlHelper<String> helper = new SqlHelper<>();
    private static final String OPTIONS_NAME_RECEIVED = "number_message_received";
    private static final String OPTIONS_NAME_TAKEN = "number_message_taken";

    private ExchangeMessageService() {
    }

    public static ExchangeMessage getMessages() {
        ExchangeMessage exchangeMessage = new ExchangeMessage();
        String received = helper.getFirst("app_options", "options_value", "options_name",
                "where options_name = '" + OPTIONS_NAME_RECEIVED + "'");
        String taken = helper.getFirst("app_options", "options_value", "options_name",
                "where options_name = '" + OPTIONS_NAME_TAKEN + "'");
        exchangeMessage.setReceivedNumber(Long.parseLong(received));
        exchangeMessage.setTakenNumber(Long.parseLong(taken));
        return exchangeMessage;
    }

    public static void saveMessages(ExchangeMessage exchangeMessage) throws SQLException {
        List<DatabaseRequest> requestList = new ArrayList<>();
        requestList.add(getUpdateReceivedRequest(exchangeMessage));
        requestList.add(getUpdateTakenRequest(exchangeMessage));
        helper.execSql(requestList);
    }

    public static DatabaseRequest getUpdateReceivedRequest(ExchangeMessage exchangeMessage) {
        String sql = """
                update app_options set
                    options_value = ?
                where options_name = ?
                """;
        StatementParameters<Object> parameters = StatementParameters.build(
                String.valueOf(exchangeMessage.getReceivedNumber()),
                OPTIONS_NAME_RECEIVED
        );

        return new DatabaseRequest(sql, parameters);
    }

    public static DatabaseRequest getUpdateTakenRequest(ExchangeMessage exchangeMessage) {
        String sql = """
                update app_options set
                    options_value = ?
                where options_name = ?
                """;
        StatementParameters<Object> parameters = StatementParameters.build(
                String.valueOf(exchangeMessage.getTakenNumber()),
                OPTIONS_NAME_TAKEN
        );

        return new DatabaseRequest(sql, parameters);
    }
}
