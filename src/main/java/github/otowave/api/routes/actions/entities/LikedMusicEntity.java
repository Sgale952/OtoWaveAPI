package github.otowave.api.routes.actions.entities;

import github.otowave.api.routes.common.entities.ActionsEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "liked_music", schema = "actions")
public class LikedMusicEntity extends ActionsEntity {
    public LikedMusicEntity() {
    }
}
