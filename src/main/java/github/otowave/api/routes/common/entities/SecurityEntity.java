package github.otowave.api.routes.common.entities;

import org.springframework.data.annotation.Id;

public class SecurityEntity {
    @Id
    private int itemID;
    private boolean access;

    public int getItemID() {
        return itemID;
    }
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public boolean isAccess() {
        return access;
    }
    public void setAccess(boolean access) {
        this.access = access;
    }
}
