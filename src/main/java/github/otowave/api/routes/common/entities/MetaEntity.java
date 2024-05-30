package github.otowave.api.routes.common.entities;

import java.time.LocalDateTime;

public class MetaEntity {
    private int itemID;
    private String tale;
    private int likes;
    private LocalDateTime created;

    public int getItemID() {
        return itemID;
    }
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getTale() {
        return tale;
    }
    public void setTale(String tale) {
        this.tale = tale;
    }

    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }

    public LocalDateTime getCreated() {
        return created;
    }
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
