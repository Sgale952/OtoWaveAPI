package github.otowave.api.routes.music.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "musicMeta", schema = "music")
public class MusicMetaEntity {
    @Id
    private int musicID;
    private String tale;
    private int likes;
    private int listens;
    private LocalDateTime uploaded;

    public MusicMetaEntity() {

    }

    public int getMusicID() {
        return musicID;
    }
    public void setMusicID(int musicID) {
        this.musicID = musicID;
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

    public int getListens() {
        return listens;
    }
    public void setListens(int listens) {
        this.listens = listens;
    }

    public LocalDateTime getUploaded() {
        return uploaded;
    }
    public void setUploaded(LocalDateTime uploaded) {
        this.uploaded = uploaded;
    }
}
