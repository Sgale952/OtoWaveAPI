package github.otowave.api.routes.music.entities;

import github.otowave.api.routes.common.entities.ProfileEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "profile", schema = "music")
public class MusicProfileEntity extends ProfileEntity {
    private int authorID;
    private String title;
    private String genre;
    private boolean econtent;

    public MusicProfileEntity() {
    }

    public int getAuthorID() {
        return authorID;
    }
    public void setAuthorID(int authorID) {
        this.authorID = authorID;
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

    public boolean getEcontent() {
        return econtent;
    }
    public void setEcontent(boolean econtent) {
        this.econtent = econtent;
    }
}
