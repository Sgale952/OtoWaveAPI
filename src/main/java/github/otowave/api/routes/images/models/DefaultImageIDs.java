package github.otowave.api.routes.images.models;

public enum DefaultImageIDs {
    USERS(1),
    USER_HEADER(2),
    MUSIC(3),
    PLAYLISTS(4),
    ALBUMS(5);

    private final int imageID;

    DefaultImageIDs(int imageID) {
        this.imageID = imageID;
    }

    public static DefaultImageIDs toDefaultImageID(String itemType) {
        return valueOf(itemType.toUpperCase());
    }

    public int getImageID() {
        return imageID;
    }
}
