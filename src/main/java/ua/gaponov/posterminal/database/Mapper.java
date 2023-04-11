package ua.gaponov.posterminal.database;

import java.sql.ResultSet;

/**
 *
 * @author wmcon
 */
public interface Mapper<T> {
    T map(ResultSet rs);
}
