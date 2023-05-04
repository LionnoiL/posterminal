package ua.gaponov.posterminal.users;

import ua.gaponov.posterminal.database.SqlHelper;
import ua.gaponov.posterminal.database.StatementParameters;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static ua.gaponov.posterminal.utils.AppProperties.currentUser;

/**
 * @author Andriy Gaponov
 */
public class UserService {

    public static List<User> getAll() {
        return new SqlHelper<User>().getAll("SELECT * FROM users", new UserDatabaseMapper());
    }

    public static List<User> getAllActive() {
        StatementParameters<Boolean> parameters = StatementParameters.build(true);
        return new SqlHelper<User>().getAll("SELECT * FROM users where active = ?",
                parameters,
                new UserDatabaseMapper());
    }

    public static User getByName(String userName) {

        StatementParameters<String> parameters = StatementParameters.build(userName);
        return new SqlHelper<User>().getOne("select * from users where user_name = ?",
                parameters,
                new UserDatabaseMapper());
    }

    public static boolean login(String userName, String password) {
        User user = UserService.getByName(userName);
        if (user.getPassword().isEmpty() && password.isEmpty()) {
            currentUser = user;
            return true;
        }
        String encryptPassword = UserService.encryptPassword(password);
        boolean passwordEquals = encryptPassword.equals(user.getPassword());
        if (passwordEquals) {
            currentUser = user;
        } else {
            currentUser = null;
        }
        return passwordEquals;
    }


    public static String encryptPassword(String password) {
        String encryptedpassword = null;
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(password.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            encryptedpassword = s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return encryptedpassword;
    }
}
