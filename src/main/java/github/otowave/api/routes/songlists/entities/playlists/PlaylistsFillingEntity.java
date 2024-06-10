package github.otowave.api.routes.songlists.entities.playlists;

import github.otowave.api.routes.common.entities.FillingEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "filling", schema = "playlists")
public class PlaylistsFillingEntity extends FillingEntity {
    public PlaylistsFillingEntity() {
    }

    public PlaylistsFillingEntity(int itemID, int musicID) {
        setItemID(itemID);
        setMusicID(musicID);
    }
}
