package github.otowave.api.entities.actions;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "likedMusic", schema = "actions")
public class LikedMusicEntity {
    @Id
    private int userID;
    private int musicID;
    private LocalDateTime liked;

    public LikedMusicEntity() {

    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMusicID() {
        return musicID;
    }
    public void setMusicID(int musicID) {
        this.musicID = musicID;
    }

    public LocalDateTime getLiked() {
        return liked;
    }
    public void setLiked(LocalDateTime liked) {
        this.liked = liked;
    }
}
