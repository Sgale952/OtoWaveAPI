package github.otowave.api.routes.common.models;

public class FaceModel {
    private final int itemID;
    private final int authorID;
    private final int coverID;
    private final String username;

    public FaceModel(int itemID, int authorID, int coverID, String username) {
        this.itemID = itemID;
        this.authorID = authorID;
        this.coverID = coverID;
        this.username = username;
    }

    public int getItemID() {
        return itemID;
    }

    public int getAuthorID() {
        return authorID;
    }

    public int getCoverID() {
        return coverID;
    }

    public String getUsername() {
        return username;
    }
}
