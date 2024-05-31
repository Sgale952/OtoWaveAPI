package github.otowave.api.routes.songlists.entities.playlists;

import github.otowave.api.routes.common.entities.SecurityEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "security", schema = "playlists")
public class PlaylistsSecurityEntity extends SecurityEntity {
    public PlaylistsSecurityEntity() {
    }

    public PlaylistsSecurityEntity(boolean access) {
        setAccess(access);
    }
}
