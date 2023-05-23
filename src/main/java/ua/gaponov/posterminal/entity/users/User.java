package ua.gaponov.posterminal.entity.users;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
public class User {

    private String guid;
    private String name;
    private String password;
    private boolean active;
    private UserRole role;


    public boolean isAdmin() {
        return Objects.equals(UserRole.ADMIN, role);
    }
}
