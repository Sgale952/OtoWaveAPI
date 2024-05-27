package github.otowave.api.routes.users.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "users_meta", schema = "users")
public class UsersMetaEntity {
    @Id
    private int userID;
    private String tale;
    private int listens;
    private int likes;
    private LocalDateTime created;

    public UsersMetaEntity() {
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTale() {
        return tale;
    }
    public void setTale(String tale) {
        this.tale = tale;
    }

    public int getListens() {
        return listens;
    }
    public void setListens(int listens) {
        this.listens = listens;
    }

    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
