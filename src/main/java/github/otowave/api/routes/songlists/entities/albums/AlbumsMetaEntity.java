package github.otowave.api.routes.songlists.entities.albums;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "meta", schema = "albums")
public class AlbumsMetaEntity {
    @Id
    private int albumsID;
    private String tale;
    private int likes;
    private LocalDateTime created;

    public AlbumsMetaEntity() {
    }

    public int getAlbumsID() {
        return albumsID;
    }
    public void setAlbumsID(int albumsID) {
        this.albumsID = albumsID;
    }

    public String getTale() {
        return tale;
    }
    public void setTale(String tale) {
        this.tale = tale;
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
