package github.otowave.api.routes.songlists.entities.playlists;

import github.otowave.api.routes.common.entities.ProfileEntity;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "profile", schema = "playlists")
public class PlaylistsProfileEntity extends ProfileEntity {
    private int creatorID;
    private String title;
    private String genre;
    private boolean econtent;

    public PlaylistsProfileEntity() {
    }

    public PlaylistsProfileEntity(int creatorID, String title) {
        this.creatorID = creatorID;
        this.title = title;
        setCoverID(DefaultImageIDs.PLAYLISTS.getImageID());
    }

    public int getCreatorID() {
        return creatorID;
    }
    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
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
