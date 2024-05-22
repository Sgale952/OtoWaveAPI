package github.otowave.api.routes.actions.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

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
