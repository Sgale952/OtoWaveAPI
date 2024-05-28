package github.otowave.api.routes.songlists.entities.playlists;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "profile", schema = "playlists")
public class PlaylistsProfileEntity {
    @Id
    private int playlistID;
    private int creatorID;
    private int coverID;
    private String title;
    private boolean access;

    public PlaylistsProfileEntity() {
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
}
