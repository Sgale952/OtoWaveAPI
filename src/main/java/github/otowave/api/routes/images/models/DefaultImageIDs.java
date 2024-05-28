package github.otowave.api.routes.images.models;

public enum DefaultImageIDs {
    USER(1),
    USER_HEADER(2),
    MUSIC(3),
    PLAYLIST(4),
    ALBUM(5);

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
