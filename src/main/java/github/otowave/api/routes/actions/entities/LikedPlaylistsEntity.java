package github.otowave.api.routes.actions.entities;

import github.otowave.api.routes.common.entities.ActionsEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "liked_playlists", schema = "actions")
public class LikedPlaylistsEntity extends ActionsEntity {
    public LikedPlaylistsEntity() {
    }

    public LikedPlaylistsEntity(int userID, int playlistID) {
        setUserID(userID);
        setItemID(playlistID);
    }
}
