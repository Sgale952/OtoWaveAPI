package github.otowave.api.routes.playlists.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "filling_playlists", schema = "playlists")
public class FillingPlaylistsEntity {
    @Id
    private int playlistID;
    private int musicID;
    private LocalDateTime added;

    public FillingPlaylistsEntity() {
    }

    public int getPlaylistID() {
        return playlistID;
    }
    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
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
