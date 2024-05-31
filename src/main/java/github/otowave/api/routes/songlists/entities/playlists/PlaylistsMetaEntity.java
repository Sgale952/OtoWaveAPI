package github.otowave.api.routes.songlists.entities.playlists;

import github.otowave.api.routes.common.entities.MetaEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "meta", schema = "playlists")
public class PlaylistsMetaEntity extends MetaEntity {
    public PlaylistsMetaEntity() {
    }

    public PlaylistsMetaEntity(String tale) {
        setTale(tale);
    }
}
