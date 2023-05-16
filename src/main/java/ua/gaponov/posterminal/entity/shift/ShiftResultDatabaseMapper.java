package ua.gaponov.posterminal.entity.shift;

import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
public class ShiftResultDatabaseMapper implements Mapper<ShiftResult> {

    @Override
    public ShiftResult map(ResultSet rs) {
        try {
            ShiftResult shiftResult = new ShiftResult();
            shiftResult.setId(rs.getLong("id"));
            shiftResult.setDate(rs.getTimestamp("doc_date").toLocalDateTime());
            shiftResult.setUserGuid(rs.getString("user_guid"));
            shiftResult.setSummaOrderCash(rs.getDouble("summa_order_cash"));
            shiftResult.setSummaReturnCash(rs.getDouble("summa_return_cash"));
            shiftResult.setSummaSafe(rs.getDouble("summa_safe"));
            shiftResult.setSummaOrderCard(rs.getDouble("summa_order_card"));
            shiftResult.setSummaMoneyMoveIn(rs.getDouble("summa_money_move_in"));
            shiftResult.setSummaMoneyMoveOut(rs.getDouble("summa_money_move_out"));
            shiftResult.setSummaSale(rs.getDouble("summa_sale"));
            shiftResult.setSummaReturn(rs.getDouble("summa_return"));

            return shiftResult;
        } catch (SQLException e) {
            new MapperException("Error map ShiftResult");
        }
        return null;
    }
}
