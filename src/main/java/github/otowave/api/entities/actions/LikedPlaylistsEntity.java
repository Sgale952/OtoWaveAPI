package github.otowave.api.entities.actions;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "likedPlaylists", schema = "actions")
public class LikedPlaylistsEntity {
    @Id
    private int userID;
    private int playlistID;
    private LocalDateTime liked;

    public LikedPlaylistsEntity() {

    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getPlaylistID() {
        return playlistID;
    }
    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }

    public LocalDateTime getLiked() {
        return liked;
    }
    public void setLiked(LocalDateTime liked) {
        this.liked = liked;
    }
}
