package ua.gaponov.posterminal.entity.shift;

import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.entity.orders.Order;
import ua.gaponov.posterminal.entity.products.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * @author Andriy Gaponov
 */
public class ShiftResultService {

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
                shiftResult.getSummaCard(),
                shiftResult.getSummaMoneyMoveIn(),
                shiftResult.getSummaMoneyMoveOut(),
                shiftResult.getSummaSale(),
                shiftResult.getSummaReturn()
        );

        String sql = """
                    insert into shift_results (USER_GUID, SUMMA_ORDER_CASH, SUMMA_RETURN_CASH,
                                SUMMA_SAFE, SUMMA_ORDER_CARD, SUMMA_CARD, SUMMA_MONEY_MOVE_IN,
                                SUMMA_MONEY_MOVE_OUT, SUMMA_SALE, SUMMA_RETURN)
                    values
                                (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        new SqlHelper<Product>().execSql(sql, parameters);
    }

    public static double getTotalSumSafe(){
        Double last = new SqlHelper<Double>().getLast("shift_results", "SUMMA_SAFE", "id");
        if (Objects.isNull(last)){
            return 0;
        } else {
            return last;
        }
    }
}
