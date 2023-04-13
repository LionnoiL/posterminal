package ua.gaponov.posterminal.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import ua.gaponov.posterminal.database.Mapper;

/**
 *
 * @author wmcon
 */
public class UserDatabaseMapper implements Mapper<User> {

    @Override
    public User map(ResultSet rs) {
        try {
            User user = new User();
            user.setGuid(rs.getString("user_guid"));
            user.setName(rs.getString("user_name"));
            user.setPassword(rs.getString("user_password"));
            user.setRole(UserRole.valueOf(rs.getString("user_role")));
            user.setActive(rs.getBoolean("active"));
           
            return user;
        } catch (SQLException e) {
            new RuntimeException();
        }
        return null;
    }
}
