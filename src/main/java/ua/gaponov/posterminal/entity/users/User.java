package ua.gaponov.posterminal.entity.users;

import java.util.Objects;

/**
 * @author Andriy Gaponov
 */
public class User {

    private String guid;
    private String name;
    private String password;
    private boolean active;
    private UserRole role;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return Objects.equals(UserRole.ADMIN, role);
    }
}
