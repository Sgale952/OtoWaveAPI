package github.otowave.api.entities.playlists;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "playlists", schema = "playlists")
public class PlaylistsEntity {
    @Id
    private int playlistID;
    private int creatorID;
    private int coverID;
    private String title;
    private boolean access;
    private LocalDateTime created;

    public PlaylistsEntity() {

    }

    public int getPlaylistID() {
        return playlistID;
    }
    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
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

    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
