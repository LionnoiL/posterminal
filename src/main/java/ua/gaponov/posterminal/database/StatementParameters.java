package ua.gaponov.posterminal.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wmcon
 */
public class StatementParameters<T, E> {
    
    private final List<Parametr> items = new ArrayList<Parametr>();

    public StatementParameters() {
    }

    public StatementParameters(T value, E value2) {
        items.add(new Parametr<T>(value));
        items.add(new Parametr<E>(value2));
    }

    public StatementParameters(T value) {
        items.add(new Parametr<T>(value));
    }

    public void add(Parametr parametr) {
        items.add(parametr);
    }

    public void add(T value) {
        items.add(new Parametr<T>(value));
    }

    public List<Parametr> getItems() {
        return items;
    }

    public void fillStatement(PreparedStatement statement) throws SQLException {
        List<Parametr> params = getItems();
        int index = 1;
        for (Parametr p : params) {
            statement.setObject(index++, p.getValue());
        }
    }

}
