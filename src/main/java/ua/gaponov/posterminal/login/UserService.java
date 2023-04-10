/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ua.gaponov.posterminal.login;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ua.gaponov.posterminal.database.SqlHelper;

/**
 *
 * @author gaponov
 */
public class UserService {
    
    public static List<User> getAll(){
        List<User> result = new ArrayList<>();
        String sql = "select * from users";

        SqlHelper.executeQuery(sql, rs -> {
            try {
                while (rs.next()) {
                    User user = new User();
                    user.setGuid(rs.getString("user_guid"));
                    user.setName(rs.getString("user_name"));
                    result.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        return result;
    }
}
