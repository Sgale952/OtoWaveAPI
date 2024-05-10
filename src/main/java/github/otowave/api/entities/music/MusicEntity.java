package github.otowave.api.entities.music;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.time.LocalDateTime;

@Entity
@Table(name = "music", schema = "music")
public class MusicEntity {
    @Id
    private int musicID;
    private int authorID;
    private int coverID;
    private String title;
    private String genre;
    private int econtent;
    private LocalDateTime uploaded;

    public MusicEntity() {

    }

    public int getMusicID() {
        return musicID;
    }
    public void setMusicID(int musicID) {
        this.musicID = musicID;
    }

    public int getAuthorID() {
        return authorID;
    }
    public void setAuthorID(int authorID) {
        this.authorID = authorID;
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

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getEcontent() {
        return econtent;
    }
    public void setEcontent(int econtent) {
        this.econtent = econtent;
    }

    public LocalDateTime getUploaded() {
        return uploaded;
    }
    public void setUploaded(LocalDateTime uploaded) {
        this.uploaded = uploaded;
    }
}
