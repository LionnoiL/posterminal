package ua.gaponov.posterminal.entity.moneymove;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.database.DatabaseRequest;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.entity.MoveType;
import ua.gaponov.posterminal.entity.shift.ShiftResultService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        requestList.add(getInsertShiftResultRequest(moneyMove));
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

    public static DatabaseRequest getInsertShiftResultRequest(MoneyMove moneyMove) {
        String sql = """
                    insert into shift_results (USER_GUID, SUMMA_MONEY_MOVE_IN, SUMMA_MONEY_MOVE_OUT, SUMMA_SAFE)
                    values
                                (?, ?, ?, ?)
                """;
        double summaMoneyIn = 0;
        double summaMoneyOut = 0;
        double summaSafe = 0;


        if (Objects.equals(MoveType.MOVE_IN, moneyMove.getMoveType())) {
            summaMoneyIn = moneyMove.getDocumentSum();
            summaSafe = ShiftResultService.getTotalSumSafe() + summaMoneyIn;
        } else if (Objects.equals(MoveType.MOVE_OUT, moneyMove.getMoveType())) {
            summaMoneyOut = moneyMove.getDocumentSum();
            summaSafe = ShiftResultService.getTotalSumSafe() - summaMoneyOut;
        }

        StatementParameters<Object> parameters = StatementParameters.build(
                AppProperties.getCurrentUser().getGuid(),
                summaMoneyIn,
                summaMoneyOut,
                summaSafe
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
