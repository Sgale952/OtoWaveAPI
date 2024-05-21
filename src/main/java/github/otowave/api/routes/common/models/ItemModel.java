package github.otowave.api.routes.common.models;

import static github.otowave.api.security.Verifier.verify;

public record ItemModel(ItemTypes itemType, int itemID, int uploaderID) {
    public ItemModel(ItemTypes itemType, int itemID) {
        this(itemType, itemID, -1);
    }

    public ItemModel(ItemTypes itemType, int itemID, String authToken) {
        this(itemType, itemID, verify(authToken, itemID));
    }
}