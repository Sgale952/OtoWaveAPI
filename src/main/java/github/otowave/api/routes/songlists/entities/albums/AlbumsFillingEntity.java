package github.otowave.api.routes.songlists.entities.albums;

import github.otowave.api.routes.common.entities.FillingEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "filling", schema = "albums")
public class AlbumsFillingEntity extends FillingEntity {
    public AlbumsFillingEntity() {
    }

    public AlbumsFillingEntity(int itemID, int musicID) {
        setItemID(itemID);
        setMusicID(musicID);
    }
}
