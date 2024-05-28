package github.otowave.api.routes.songlists.models;

public enum SonglistTypes {
    PLAYLIST,
    ALBUM;

    public static SonglistTypes toSonglistType(String itemType) {
        return valueOf(itemType.toUpperCase());
    }
}
