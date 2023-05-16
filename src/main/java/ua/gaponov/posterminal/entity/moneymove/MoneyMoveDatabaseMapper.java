package ua.gaponov.posterminal.entity.moneymove;

import lombok.extern.slf4j.Slf4j;
import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;
import ua.gaponov.posterminal.entity.MoveType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
@Slf4j
public class MoneyMoveDatabaseMapper implements Mapper<MoneyMove> {

    @Override
    public MoneyMove map(ResultSet rs) {
        try {
            MoneyMove moneyMove = MoneyMove.of(
                    rs.getString("money_move_guid"),
                    rs.getLong("money_move_number"),
                    rs.getTimestamp("money_move_date").toLocalDateTime(),
                    rs.getBoolean("upload"),
                    rs.getDouble("summa_doc"),
                    MoveType.valueOf(rs.getString("move_type")),
                    rs.getString("comment_doc")
            );

            return moneyMove;
        } catch (SQLException ex) {
            log.error("Mapping money move failed", ex);
            new MapperException("Error map money move");
        }
        return null;
    }
}
