package ua.gaponov.posterminal.entity.shift;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.utils.DateUtils;

import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ShiftResultService {

    private static final Logger LOG = LoggerFactory.getLogger(ShiftResultService.class);
    private static final SqlHelper<ShiftResultService> SQL_HELPER = new SqlHelper<>();

    public static ShiftResult1C getCurrentShift(){
        StatementParameters<String> parameters = StatementParameters.build(DateUtils.getDateTimeNow("yyyy-MM-dd"));
        String sql = """
                select * from shift_totals where doc_date = ?
                """;

        return new SqlHelper<ShiftResult1C>().getOne(sql,
                parameters,
                new ShiftResult1CDatabaseMapper());
    }
    public static void save1cResults(ShiftResult1C shiftResult1C) throws SQLException {
        ShiftResult1C currentShift = getCurrentShift();

        StatementParameters<Object> parameters = StatementParameters.build(
                shiftResult1C.getDocDate(),
                shiftResult1C.getCashStart(),
                shiftResult1C.getCashEnd(),
                shiftResult1C.getSummaSale(),
                shiftResult1C.getSummaReturn(),
                shiftResult1C.getSaleCash(),
                shiftResult1C.getSaleCard(),
                shiftResult1C.getMoneyIn(),
                shiftResult1C.getMoneyOut()
        );

        String sql = "";

        if (currentShift == null){
            sql = """
                    insert into shift_totals (doc_date, cash_start, cash_end,
                                summa_sale, summa_return, sale_cash,
                                sale_card, money_in, money_out)
                    values
                                (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        } else {
            parameters.addAll(shiftResult1C.getDocDate());
            sql = """
                    update shift_totals 
                    set doc_date = ?, 
                    cash_start = ?, 
                    cash_end = ?,
                    summa_sale = ?, 
                    summa_return = ?, 
                    sale_cash = ?,
                    sale_card = ?, 
                    money_in = ?, 
                    money_out = ?
                    where doc_date = ?
                """;
        }

        SQL_HELPER.execSql(sql, parameters);
    }
}
