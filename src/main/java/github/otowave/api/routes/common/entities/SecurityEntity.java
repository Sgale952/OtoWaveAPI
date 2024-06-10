package github.otowave.api.routes.common.entities;

public class SecurityEntity {
    private int itemID;
    private boolean access;

    public SecurityEntity() {
    }

    public SecurityEntity(boolean access) {
        this.access = access;
    }

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
