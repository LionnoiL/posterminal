package ua.gaponov.posterminal.entity.users;

import ua.gaponov.posterminal.database.Mapper;
import ua.gaponov.posterminal.database.MapperException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andriy Gaponov
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
            new MapperException("Error map user");
        }
        return null;
    }
}
