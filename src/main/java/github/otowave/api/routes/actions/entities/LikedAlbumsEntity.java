package github.otowave.api.routes.actions.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "liked_albums", schema = "actions")
public class LikedAlbumsEntity {
    @Id
    private int userID;
    private int albumID;
    private LocalDateTime liked;

    public LikedAlbumsEntity() {
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAlbumID() {
        return albumID;
    }
    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public LocalDateTime getLiked() {
        return liked;
    }
    public void setLiked(LocalDateTime liked) {
        this.liked = liked;
    }
}
