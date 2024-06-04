package github.otowave.api.routes.users.entities;

import github.otowave.api.routes.common.entities.SecurityEntity;
import github.otowave.api.routes.users.models.UserRole;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "security", schema = "users")
public class UsersSecurityEntity extends SecurityEntity {
    private String email;
    private String password;
    private String accessRole;

    public UsersSecurityEntity() {
    }

    public UsersSecurityEntity(String email, String password) {
        this.email = email;
        this.password = password;
        this.accessRole = UserRole.AUTHOR.name();
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessRole() {
        return accessRole;
    }
    public void setAccessRole(String accessRole) {
        this.accessRole = accessRole;
    }
}
