package github.otowave.api.routes.common.entities;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class ActionsEntity {
    @Id
    private int userID;
    private int itemID;
    private LocalDateTime added;

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getItemID() {
        return itemID;
    }
    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public LocalDateTime getAdded() {
        return added;
    }
    public void setAdded(LocalDateTime added) {
        this.added = added;
    }
}
