package github.otowave.api.routes.users.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "profile", schema = "users")
public class UsersProfileEntity {
    @Id
    private int userID;
    private int avatarID;
    private int headerID;
    private String username;

    public UsersProfileEntity() {
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAvatarID() {
        return avatarID;
    }
    public void setAvatarID(int avatarID) {
        this.avatarID = avatarID;
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
