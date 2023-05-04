package ua.gaponov.posterminal.cards;

import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
public class CardDatabaseMapper implements Mapper<Card> {

    @Override
    public Card map(ResultSet rs) {
        try {
            Card card = new Card();
            card.setGuid(rs.getString("card_guid"));
            card.setCode(rs.getString("code"));
            card.setActive(rs.getBoolean("active"));
            card.setCardType(CardType.valueOf(rs.getString("card_type")));
            card.setClientName(rs.getString("client_name"));
            card.setClientPhone(rs.getString("client_phone"));
            card.setClientEmail(rs.getString("client_email"));
            card.setDiscount(rs.getDouble("discount"));
            card.setDebt(rs.getDouble("debt"));
            card.setDebtAllowed(rs.getBoolean("debt_allowed"));
            card.setMaxDebt(rs.getDouble("max_debt"));
            card.setMaxDebtDay(rs.getInt("max_debt_day"));
            return card;
        } catch (SQLException e) {
            new MapperException("Error map card");
        }
        return null;
    }
}
