/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.gaponov.posterminal.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;

/**
 *
 * @author gaponov
 */
public class Database {
    
    private static BasicDataSource dataSource = null;

    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:./pos");
        dataSource.setUsername("");
        dataSource.setPassword("");

        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxTotal(25);

        migrate();
    }

    private Database() {

    }

    public static void setDataSource(BasicDataSource dataSource) {
        Database.dataSource = dataSource;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
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
