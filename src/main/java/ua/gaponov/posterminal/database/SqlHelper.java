/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.gaponov.posterminal.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

/**
 *
 * @author gaponov
 */
public class SqlHelper {
    
    private SqlHelper(){
    }
            
    public static void executeQuery(String sql, Consumer<ResultSet> consumer) {

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()
        ) {
            consumer.accept(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
