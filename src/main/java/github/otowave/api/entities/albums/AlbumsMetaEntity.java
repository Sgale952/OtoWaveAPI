package github.otowave.api.entities.albums;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "albumsMeta", schema = "albums")
public class AlbumsMetaEntity {
    @Id
    private int albumsID;
    private String tale;
    private int likes;

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
}
