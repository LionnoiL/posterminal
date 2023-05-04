/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.gaponov.posterminal.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
public class Database {

    private static BasicDataSource dataSource = null;

    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:./pos;TRACE_LEVEL_FILE=0;DEFRAG_ALWAYS=TRUE");
        dataSource.setUsername("");
        dataSource.setPassword("");

        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(25);
        dataSource.setMaxOpenPreparedStatements(100);

        migrate();
    }

    private Database() {

    }

    public static void setDataSource(BasicDataSource dataSource) {
        Database.dataSource = dataSource;
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void migrate() {
        Flyway flyway = Flyway
                .configure()
                .locations("filesystem:./sql")
                .dataSource(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword())
                .load();
        flyway.migrate();
    }
}
