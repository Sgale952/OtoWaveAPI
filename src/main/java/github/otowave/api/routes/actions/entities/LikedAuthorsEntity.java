package github.otowave.api.routes.actions.entities;

import github.otowave.api.routes.common.entities.ActionsEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "liked_authors", schema = "actions")
public class LikedAuthorsEntity extends ActionsEntity {
    public LikedAuthorsEntity() {
    }

    public LikedAuthorsEntity(int userID, int authorID) {
        setUserID(userID);
        setItemID(authorID);
    }
}
