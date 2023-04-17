package ua.gaponov.posterminal.cards;

import java.util.List;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

/**
 *
 * @author gaponov
 */
public class CardService {
    
    public static List<Card> getAll(){
       return new SqlHelper<Card>().getAll("SELECT * FROM cards", new CardDatabaseMapper());
    }
    
    public static Card getByGuid(String guid){
        StatementParameters<String, String> parameters = new StatementParameters<>(guid);
        return new SqlHelper<Card>().getOne("select * from cards where card_guid = ?",
                parameters,
                new CardDatabaseMapper());
    }
    
    public static void deleteAll(){
        SqlHelper.execSql("delete from cards");
    }

    public static void save(Card card) {

    }
}
