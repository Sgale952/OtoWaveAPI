package github.otowave.api.routes.music.entities;

import github.otowave.api.routes.common.entities.MetaEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "meta", schema = "music")
public class MusicMetaEntity extends MetaEntity {
    private int listens;

    public MusicMetaEntity() {
    }

    public MusicMetaEntity(int itemID, String tale) {
        setItemID(itemID);
        setTale(tale);
    }

    public int getListens() {
        return listens;
    }
    public void setListens(int listens) {
        this.listens = listens;
    }
}
