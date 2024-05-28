package github.otowave.api.routes.common.models.items;

public enum ItemTypes {
    MUSIC,
    PLAYLIST,
    ALBUM,
    USER,
    USER_HEADER;

    public static ItemTypes toItemType(String itemType) {
        return valueOf(itemType.toUpperCase());
    }
}
