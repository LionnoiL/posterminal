package ua.gaponov.posterminal.entity.shift;

import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
public class ShiftResult1CDatabaseMapper implements Mapper<ShiftResult1C> {

    @Override
    public ShiftResult1C map(ResultSet rs) {
        try {
            ShiftResult1C shiftResult = new ShiftResult1C();
            shiftResult.setId(rs.getLong("id"));
            shiftResult.setDocDate(rs.getString("doc_date"));
            shiftResult.setCashStart(rs.getDouble("cash_start"));
            shiftResult.setCashEnd(rs.getDouble("cash_end"));
            shiftResult.setSummaSale(rs.getDouble("summa_sale"));
            shiftResult.setSummaReturn(rs.getDouble("summa_return"));
            shiftResult.setSaleCash(rs.getDouble("sale_cash"));
            shiftResult.setSaleCard(rs.getDouble("sale_card"));
            shiftResult.setMoneyIn(rs.getDouble("money_in"));
            shiftResult.setMoneyOut(rs.getDouble("money_out"));

            return shiftResult;
        } catch (SQLException e) {
            new MapperException("Error map ShiftResultTotal");
        }
        return null;
    }
}
