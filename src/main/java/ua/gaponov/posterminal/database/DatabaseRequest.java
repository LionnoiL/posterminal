package ua.gaponov.posterminal.database;

/**
 * @author Andriy Gaponov
 */
public class DatabaseRequest {
    private String sql;
    private StatementParameters<Object> param;

    public DatabaseRequest(String sql, StatementParameters<Object> param) {
        this.sql = sql;
        this.param = param;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public StatementParameters<Object> getParam() {
        return param;
    }

    public void setParam(StatementParameters<Object> param) {
        this.param = param;
    }
}
