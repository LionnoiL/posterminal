package ua.gaponov.posterminal.cards;

import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;
import ua.gaponov.posterminal.products.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * @author gaponov
 */
public class CardService {

    private CardService() {
    }

    public static List<Card> getAll() {
        return new SqlHelper<Card>().getAll("SELECT * FROM cards", new CardDatabaseMapper());
    }

    public static Card getByGuid(String guid) {
        StatementParameters<String> parameters = StatementParameters.buildParameters(guid);
        return new SqlHelper<Card>().getOne("select * from cards where card_guid = ?",
                parameters,
                new CardDatabaseMapper());
    }

    public static Card getByCode(String code) {
        StatementParameters<String> parameters = StatementParameters.buildParameters(code);
        return new SqlHelper<Card>().getOne("select * from cards where code = ?",
                parameters,
                new CardDatabaseMapper());
    }

    public static void deleteAll() {
        SqlHelper.execSql("delete from cards");
    }

    public static void save(Card card) throws SQLException {
        Card findCard = getByGuid(card.getGuid());
        if (findCard == null) {
            insert(card);
        } else {
            update(card);
        }
    }

    private static void insert(Card card) throws SQLException {
        StatementParameters<Object> parameters = StatementParameters.buildParameters(
                card.getGuid(),
                card.isActive(),
                card.getCardType().toString(),
                card.getClientEmail(),
                card.getClientName(),
                card.getClientPhone(),
                card.getCode(),
                card.getDebt(),
                card.isDebtAllowed(),
                card.getDiscount(),
                card.getMaxDebt(),
                card.getMaxDebtDay()
        );

        String sql = """
                    insert into CARDS (
                                 CARD_GUID,
                                 ACTIVE,
                                 CARD_TYPE,
                                 CLIENT_EMAIL,
                                 CLIENT_NAME,
                                 CLIENT_PHONE,
                                 CODE,
                                 DEBT,
                                 DEBT_ALLOWED,
                                 DISCOUNT,
                                 MAX_DEBT,
                                 MAX_DEBT_DAY)
                    values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;
        new SqlHelper<Product>().execSql(sql, parameters);
    }

    private static void update(Card card) throws SQLException {
        StatementParameters<Object> parameters = StatementParameters.buildParameters(
                card.isActive(),
                card.getCardType().toString(),
                card.getClientEmail(),
                card.getClientName(),
                card.getClientPhone(),
                card.getCode(),
                card.getDebt(),
                card.isDebtAllowed(),
                card.getDiscount(),
                card.getMaxDebt(),
                card.getMaxDebtDay(),
                card.getGuid());

        String sql = """
                update CARDS set
                    ACTIVE = ?,
                    CARD_TYPE = ?,
                    CLIENT_EMAIL = ?,
                    CLIENT_NAME = ?,
                    CLIENT_PHONE = ?,
                    CODE = ?,
                    DEBT = ?,
                    DEBT_ALLOWED = ?,
                    DISCOUNT = ?,
                    MAX_DEBT = ?,
                    MAX_DEBT_DAY = ?               
                where CARD_GUID = ?
                """;
        new SqlHelper<Product>().execSql(sql, parameters);
    }
}
