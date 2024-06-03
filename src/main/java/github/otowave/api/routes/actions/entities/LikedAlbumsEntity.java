package github.otowave.api.routes.actions.entities;

import github.otowave.api.routes.common.entities.ActionsEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "liked_albums", schema = "actions")
public class LikedAlbumsEntity extends ActionsEntity {
    public LikedAlbumsEntity() {
    }

    public LikedAlbumsEntity(int userID, int albumID) {
        setUserID(userID);
        setItemID(albumID);
    }
}
