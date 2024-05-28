package github.otowave.api.routes.songlists.entities.albums;

import github.otowave.api.routes.common.entities.MetaEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "meta", schema = "albums")
public class AlbumsMetaEntity extends MetaEntity {
    public AlbumsMetaEntity() {
    }
}
