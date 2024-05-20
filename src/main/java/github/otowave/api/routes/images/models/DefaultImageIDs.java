package github.otowave.api.routes.images.models;

public enum DefaultImageIDs {
    USER_AVATAR(1),
    USER_HEADER(2),
    MUSIC_COVER(3),
    PLAYLIST_COVER(4),
    ALBUM_COVER(5);

    private final int imageID;

    DefaultImageIDs(int imageID) {
        this.imageID = imageID;
    }

    public int getImageID() {
        return imageID;
    }
}
