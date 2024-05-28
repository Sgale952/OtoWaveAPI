package github.otowave.api.routes.songlists.entities.playlists;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "filling", schema = "playlists")
public class PlaylistsFillingEntity {
    @Id
    private int playlistID;
    private int musicID;
    private LocalDateTime added;

    public PlaylistsFillingEntity() {
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
