package ua.gaponov.posterminal.entity.options;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
public class OptionsValueService implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(OptionsValueService.class);
    private static final SqlHelper<String> SQL_HELPER = new SqlHelper<>();

    public static OptionsValue getOptions(String optionsKey) {

        String optionsValueString = SQL_HELPER.getFirst("app_options", "options_value", "options_name",
                "where options_name = '" + optionsKey + "'");
        OptionsValue optionsValue = OptionsValue.builder()
                .optionsKey(optionsKey)
                .optionsValue(optionsValueString)
                .build();
        return optionsValue;
    }

    public static void updateOptionsValue(OptionsValue optionsValue) {
        String sql = """
                update app_options set
                    options_value = ?
                where options_name = ?
                """;
        StatementParameters<Object> parameters = StatementParameters.build(
                optionsValue.getOptionsValue(),
                optionsValue.getOptionsKey()
        );

        try {
            SQL_HELPER.execSql(sql, parameters);
        } catch (SQLException ex) {
            LOG.error("Update options value {} failed. {}", optionsValue.getOptionsValue(), ex);
        }
    }
}
