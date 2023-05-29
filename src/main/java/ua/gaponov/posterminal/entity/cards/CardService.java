package ua.gaponov.posterminal.entity.cards;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.gaponov.posterminal.database.DatabaseRequest;
import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CardService {

    private static final SqlHelper<Card> SQL_HELPER = new SqlHelper<>();

    public static List<Card> getAll() {
        return new SqlHelper<Card>().getAll("SELECT * FROM cards", new CardDatabaseMapper());
    }

    public static Card getByGuid(String guid) {
        StatementParameters<String> parameters = StatementParameters.build(guid);
        return new SqlHelper<Card>().getOne("select * from cards where card_guid = ?",
                parameters,
                new CardDatabaseMapper());
    }

    public static Card getByCode(String code) {
        StatementParameters<String> parameters = StatementParameters.build(code);
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
        List<DatabaseRequest> requestList = new ArrayList<>();
        requestList.add(getInsertRequest(card));
        SQL_HELPER.execSql(requestList);
    }

    private static void update(Card card) throws SQLException {
        List<DatabaseRequest> requestList = new ArrayList<>();
        requestList.add(getUpdateRequest(card));
        SQL_HELPER.execSql(requestList);
    }


    public static DatabaseRequest getUpdateRequest(Card card) {
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
        StatementParameters<Object> parameters = StatementParameters.build(
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

        return new DatabaseRequest(sql, parameters);
    }

    public static DatabaseRequest getInsertRequest(Card card) {
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

        StatementParameters<Object> parameters = StatementParameters.build(
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

        return new DatabaseRequest(sql, parameters);
    }
}
