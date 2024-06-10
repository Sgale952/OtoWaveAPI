package github.otowave.api.routes.common.entities;

import org.springframework.data.annotation.Id;

public class ProfileEntity {
    @Id
    private int itemID;
    private int coverID;

    public int getItemID() {
        return itemID;
    }
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getCoverID() {
        return coverID;
    }
    public void setCoverID(int coverID) {
        this.coverID = coverID;
    }
}
