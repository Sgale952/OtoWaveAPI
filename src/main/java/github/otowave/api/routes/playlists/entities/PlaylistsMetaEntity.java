package github.otowave.api.routes.playlists.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "playlistsMeta", schema = "playlists")
public class PlaylistsMetaEntity {
    @Id
    private int playlistID;
    private String tale;
    private int likes;

    public PlaylistsMetaEntity() {

    }

    public int getPlaylistID() {
        return playlistID;
    }
    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
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
