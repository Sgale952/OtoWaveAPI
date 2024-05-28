package github.otowave.api.routes.songlists.entities.albums;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "filling", schema = "albums")
public class AlbumsFillingEntity {
    @Id
    private int albumID;
    private int musicID;
    private LocalDateTime added;

    public AlbumsFillingEntity() {
    }

    public int getAlbumID() {
        return albumID;
    }
    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public int getMusicID() {
        return musicID;
    }
    public void setMusicID(int musicID) {
        this.musicID = musicID;
    }

    public LocalDateTime getAdded() {
        return added;
    }
    public void setAdded(LocalDateTime added) {
        this.added = added;
    }
}
