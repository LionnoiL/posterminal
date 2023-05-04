package ua.gaponov.posterminal.database;

import java.sql.ResultSet;

/**
 * @author Andriy Gaponov
 */
public interface Mapper<T> {
    T map(ResultSet rs);
}
