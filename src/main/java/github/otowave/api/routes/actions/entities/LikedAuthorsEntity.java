package github.otowave.api.routes.actions.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "likedAuthors", schema = "actions")
public class LikedAuthorsEntity {
    @Id
    private int userID;
    private int authorID;
    private LocalDateTime liked;

    public LikedAuthorsEntity() {
    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAuthorID() {
        return authorID;
    }
    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public LocalDateTime getLiked() {
        return liked;
    }
    public void setLiked(LocalDateTime liked) {
        this.liked = liked;
    }
}
