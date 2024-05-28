package github.otowave.api.routes.common.entities;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class FillingEntity {
    @Id
    private int itemID;
    private int musicID;
    private LocalDateTime added;

    public int getItemID() {
        return itemID;
    }
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getMusicID() {
        return musicID;
    }
    public void setMusicID(int musicID) {
        this.musicID = musicID;
    }

    public LocalDateTime getAdded() {
        return added;
    }
    public void setAdded(LocalDateTime added) {
        this.added = added;
    }
}
