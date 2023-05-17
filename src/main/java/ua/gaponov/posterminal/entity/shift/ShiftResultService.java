package ua.gaponov.posterminal.entity.shift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.conf.AppProperties;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.entity.products.Product;
import ua.gaponov.posterminal.entity.users.UserService;
import ua.gaponov.posterminal.utils.DateUtils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @author Andriy Gaponov
 */
public class ShiftResultService {

    private static final Logger LOG = LoggerFactory.getLogger(ShiftResultService.class);
    private static final SqlHelper<ShiftResultService> helper = new SqlHelper<>();

    private ShiftResultService() {
    }

    public static List<ShiftResult> getAll() {
        return new SqlHelper<ShiftResult>().getAll("SELECT * FROM shift_results", new ShiftResultDatabaseMapper());
    }

    public static void deleteAll() {
        SqlHelper.execSql("delete from shift_results");
    }

    public static void save(ShiftResult shiftResult) throws SQLException {
        StatementParameters<Object> parameters = StatementParameters.build(
                shiftResult.getUserGuid(),
                shiftResult.getSummaOrderCash(),
                shiftResult.getSummaReturnCash(),
                shiftResult.getSummaSafe(),
                shiftResult.getSummaOrderCard(),
                shiftResult.getSummaMoneyMoveIn(),
                shiftResult.getSummaMoneyMoveOut(),
                shiftResult.getSummaSale(),
                shiftResult.getSummaReturn()
        );

        String sql = """
                    insert into shift_results (USER_GUID, SUMMA_ORDER_CASH, SUMMA_RETURN_CASH,
                                SUMMA_SAFE, SUMMA_ORDER_CARD, SUMMA_MONEY_MOVE_IN,
                                SUMMA_MONEY_MOVE_OUT, SUMMA_SALE, SUMMA_RETURN)
                    values
                                (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        new SqlHelper<Product>().execSql(sql, parameters);
    }

    public static double getTotalSumSafe(){
        Double last = new SqlHelper<Double>().getLast("shift_results", "SUMMA_SAFE", "id", "");
        if (Objects.isNull(last)){
            return 0;
        } else {
            return last;
        }
    }

    public static double getStartSumSafe(){
        Double last = new SqlHelper<Double>().getFirst("shift_results", "SUMMA_SAFE",
                "id", " where DOC_DATE >= '"+ DateUtils.getDateTimeNow("yyyy-MM-dd 00:00:00") +"'");
        if (Objects.isNull(last)){
            return 0;
        } else {
            return last;
        }
    }

    public static String getShiftStartUserName(){
        String userGuid = new SqlHelper<String>().getFirst("shift_results", "user_guid",
                "id", " where DOC_DATE >= '"+ DateUtils.getDateTimeNow("yyyy-MM-dd 00:00:00") +"'");
        if (Objects.isNull(userGuid)){
            return "";
        } else {
            return UserService.getByGuid(userGuid).getName();
        }
    }

    public static String getShiftStartDate(){
        Timestamp dateStart = new SqlHelper<Timestamp>().getFirst("shift_results", "doc_date",
                "id", " where DOC_DATE >= '"+ DateUtils.getDateTimeNow("yyyy-MM-dd 00:00:00") +"'");
        if (Objects.isNull(dateStart)){
            return "";
        } else {
            return DateUtils.formatDateTime(dateStart.toLocalDateTime());
        }
    }

    public static ShiftResultTotal getShiftTotals(String dateTimeStart, String dateTimeEnd){
        StatementParameters<String> parameters = StatementParameters.build(dateTimeStart, dateTimeEnd);
        String sql = """
                SELECT sum(summa_order_cash) summa_order_cash, sum(SUMMA_RETURN_CASH  ) SUMMA_RETURN_CASH,
                 sum(SUMMA_ORDER_CARD  ) SUMMA_ORDER_CARD, sum(SUMMA_MONEY_MOVE_IN) SUMMA_MONEY_MOVE_IN,
                 sum(SUMMA_MONEY_MOVE_OUT) SUMMA_MONEY_MOVE_OUT, sum (SUMMA_SALE) SUMMA_SALE, sum(SUMMA_RETURN) SUMMA_RETURN\s
                FROM shift_results where DOC_DATE >= ? and DOC_DATE <= ?
                """;
        return new SqlHelper<ShiftResultTotal>().getOne(sql,
                parameters,
                new ShiftResultTotalDatabaseMapper());
    }

    public static void startShift(){
        int count = helper.getCount("select count(id) from shift_results where DOC_DATE >= '" +
                DateUtils.getDateTimeNow("yyyy-MM-dd 00:00:00") + "'");
        if (count==0){
            double totalSumSafe = getTotalSumSafe();
            ShiftResult shiftResult = new ShiftResult();
            shiftResult.setSummaSafe(totalSumSafe);
            shiftResult.setUserGuid(AppProperties.currentUser.getGuid());
            try {
                save(shiftResult);
            } catch (SQLException e) {
                LOG.error("Shift start failed {}", e);
            }
        }
    }
}
