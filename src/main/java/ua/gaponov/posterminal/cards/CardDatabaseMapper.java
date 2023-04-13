package ua.gaponov.posterminal.cards;

import java.sql.ResultSet;
import ua.gaponov.posterminal.database.Mapper;

/**
 *
 * @author gaponov
 */
public class CardDatabaseMapper implements Mapper<Card>{

    @Override
    public Card map(ResultSet rs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
