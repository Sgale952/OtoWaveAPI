package github.otowave.api.routes.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import github.otowave.api.routes.common.entities.SecurityEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Table(name = "security", schema = "users")
public class UsersSecurityEntity extends SecurityEntity implements UserDetails {
    @Id
    private Long id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private UserRole accessRole;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(accessRole.name()));
    }

    public UserRole getRole(){return accessRole;}
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
