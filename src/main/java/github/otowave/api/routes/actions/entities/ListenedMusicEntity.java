package github.otowave.api.routes.actions.entities;

import github.otowave.api.routes.common.entities.ActionsEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "listened_music", schema = "actions")
public class ListenedMusicEntity extends ActionsEntity {
    public ListenedMusicEntity() {
    }
}
