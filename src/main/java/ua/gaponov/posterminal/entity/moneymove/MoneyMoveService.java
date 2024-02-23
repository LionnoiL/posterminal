package ua.gaponov.posterminal.entity.moneymove;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.gaponov.posterminal.database.DatabaseRequest;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MoneyMoveService {

    private static final SqlHelper<MoneyMove> SQL_HELPER = new SqlHelper<>();

    public static MoneyMove getByGuid(String guid) {
        StatementParameters<String> parameters = StatementParameters.build(guid);
        return SQL_HELPER.getOne("select * from money_move where money_move_guid = ?",
                parameters,
                new MoneyMoveDatabaseMapper());
    }

    public static long getCount() {
        return SQL_HELPER.getCount("select count(money_move_guid) from money_move");
    }

    public static List<MoneyMove> getAll() {
        return SQL_HELPER.getAll("SELECT * FROM money_move", new MoneyMoveDatabaseMapper());
    }

    public static List<MoneyMove> getAllNoUpload() {
        StatementParameters<Boolean> parameters = StatementParameters.build(false);
        return SQL_HELPER.getAll("SELECT * FROM money_move where upload = ?",
                parameters,
                new MoneyMoveDatabaseMapper());
    }

    public static void deleteAll() {
        String sql = """
                    DELETE FROM money_move;
                """;
        SqlHelper.execSql(sql);
    }

    public static void deleteUploaded() {
        String sql = """
                    DELETE FROM money_move WHERE upload = true;
                """;
        SqlHelper.execSql(sql);
    }

    public static void save(MoneyMove moneyMove) throws SQLException {
        insert(moneyMove);
    }

    private static void insert(MoneyMove moneyMove) throws SQLException {
        List<DatabaseRequest> requestList = new ArrayList<>();
        requestList.add(getInsertRequest(moneyMove));
        SQL_HELPER.execSql(requestList);
    }

    public static DatabaseRequest getInsertRequest(MoneyMove moneyMove) {
        moneyMove.setMoneyMoveNumber(getCount() + 1);
        String sql = """
                    insert into money_move
                    (money_move_guid, money_move_number, summa_doc, move_type, comment_doc)
                    values
                    (?, ?, ?, ?, ?);
                """;
        StatementParameters<Object> parameters = StatementParameters.build(
                moneyMove.getGuid(),
                moneyMove.getMoneyMoveNumber(),
                moneyMove.getDocumentSum(),
                moneyMove.getMoveType().toString(),
                moneyMove.getComment()
        );

        return new DatabaseRequest(sql, parameters);
    }

    public static void confirmUploadDocument(String guid) throws SQLException {
        String sql = """
                    UPDATE money_move set upload = true WHERE money_move_guid = ?;
                """;
        StatementParameters<Object> parameters = StatementParameters.build(guid);
        SQL_HELPER.execSql(sql, parameters);
    }
}
