package github.otowave.api.routes.users.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "usersProfile", schema = "users")
public class UsersProfileEntity {
    @Id
    private int userID;
    private int avatarID;
    private int headerID;
    private String nickname;
    private String tale;
    private LocalDateTime created;

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

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTale() {
        return tale;
    }
    public void setTale(String tale) {
        this.tale = tale;
    }

    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}