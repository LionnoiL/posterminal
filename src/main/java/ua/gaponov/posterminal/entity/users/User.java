package ua.gaponov.posterminal.entity.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
public class User implements Serializable {

    private String guid;
    private String name;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean active;
    @JsonIgnore
    private UserRole role;


    public boolean isAdmin() {
        return Objects.equals(UserRole.ADMIN, role);
    }
}
