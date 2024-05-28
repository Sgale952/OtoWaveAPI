package github.otowave.api.routes.songlists.entities.playlists;

import github.otowave.api.routes.common.entities.ProfileEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "profile", schema = "playlists")
public class PlaylistsProfileEntity extends ProfileEntity {
    private int creatorID;
    private String title;
    private boolean access;

    public PlaylistsProfileEntity() {
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

    public boolean isAccess() {
        return access;
    }
    public void setAccess(boolean access) {
        this.access = access;
    }
}
