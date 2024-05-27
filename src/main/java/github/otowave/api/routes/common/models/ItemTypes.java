package github.otowave.api.routes.common.models;

public enum ItemTypes {
    MUSIC("music"),
    PLAYLIST("playlist"),
    ALBUM("album"),
    USER("user"),
    USER_HEADER("userHeader");

    ItemTypes(String type) {
    }

    public static ItemTypes toItemType(String itemType) {
        return valueOf(itemType.toUpperCase());
    }
}
