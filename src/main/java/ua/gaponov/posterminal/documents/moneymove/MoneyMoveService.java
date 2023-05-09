package ua.gaponov.posterminal.documents.moneymove;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ua.gaponov.posterminal.database.DatabaseRequest;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andriy Gaponov
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MoneyMoveService {

    private static final SqlHelper<MoneyMove> helper = new SqlHelper<>();

    public static MoneyMove getByGuid(String guid) {
        StatementParameters<String> parameters = StatementParameters.build(guid);
        return helper.getOne("select * from money_move where money_move_guid = ?",
                parameters,
                new MoneyMoveDatabaseMapper());
    }

    public static long getCount() {
        return helper.getCount("select count(money_move_guid) from money_move");
    }

    public static List<MoneyMove> getAll() {
        return helper.getAll("SELECT * FROM money_move", new MoneyMoveDatabaseMapper());
    }

    public static List<MoneyMove> getAllNoUpload() {
        StatementParameters<Boolean> parameters = StatementParameters.build(false);
        return helper.getAll("SELECT * FROM money_move where upload = ?",
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
        helper.execSql(requestList);
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
}
