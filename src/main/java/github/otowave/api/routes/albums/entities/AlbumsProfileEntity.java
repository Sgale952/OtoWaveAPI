package github.otowave.api.routes.albums.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "profile", schema = "albums")
public class AlbumsProfileEntity {
    @Id
    private int albumID;
    private int creatorID;
    private int coverID;
    private String title;
    private boolean access;

    public AlbumsProfileEntity() {
    }

    public int getAlbumID() {
        return albumID;
    }
    public void setAlbumID(int albumID) {
        this.albumID = albumID;
    }

    public int getCreatorID() {
        return creatorID;
    }
    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public int getCoverID() {
        return coverID;
    }
    public void setCoverID(int coverID) {
        this.coverID = coverID;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAccess() {
        return access;
    }
    public void setAccess(boolean access) {
        this.access = access;
    }
}
