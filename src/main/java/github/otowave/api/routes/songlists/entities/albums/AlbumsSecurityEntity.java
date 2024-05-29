package github.otowave.api.routes.songlists.entities.albums;

import github.otowave.api.routes.common.entities.SecurityEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "security", schema = "albums")
public class AlbumsSecurityEntity extends SecurityEntity {
    public AlbumsSecurityEntity() {
    }
}
