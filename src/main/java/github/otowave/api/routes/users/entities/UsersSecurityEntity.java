package github.otowave.api.routes.users.entities;

import github.otowave.api.routes.common.entities.SecurityEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "security", schema = "users")
public class UsersSecurityEntity extends SecurityEntity {
    private String email;
    private String passwrd;
    private String accessRole;

    public UsersSecurityEntity() {
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswrd() {
        return passwrd;
    }
    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    public String getAccessRole() {
        return accessRole;
    }
    public void setAccessRole(String accessRole) {
        this.accessRole = accessRole;
    }
}
