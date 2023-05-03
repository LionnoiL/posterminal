package ua.gaponov.posterminal.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wmcon
 */
public class StatementParameters<T> {
    
    private final List<Parametr> items = new ArrayList<>();

    public StatementParameters() {
    }

    public StatementParameters(T value) {
        items.add(new Parametr<T>(value));
    }

    private void add(Parametr parametr) {
        items.add(parametr);
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

    public <T> void addAll(T... elements){
        for (T element : elements) {
            add(new Parametr(element));
        }
    }

    public void addNull(){
        add(new Parametr(null));
    }

    public static<T> StatementParameters<T> build(T... elements){
        StatementParameters<T> result = new StatementParameters<T>();
        result.addAll(elements);
        return result;
    }

}
