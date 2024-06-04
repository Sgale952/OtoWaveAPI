package github.otowave.api.routes.users.entities;

import github.otowave.api.routes.common.entities.ProfileEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "profile", schema = "users")
public class UsersProfileEntity extends ProfileEntity {
    private int headerID;
    private String username;

    public UsersProfileEntity() {
    }

    public UsersProfileEntity(int userID, String username) {
        setItemID(userID);
        this.username = username;
    }

    public int getHeaderID() {
        return headerID;
    }
    public void setHeaderID(int headerID) {
        this.headerID = headerID;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
