package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.exceptions.InvalidItemTypeException;
import github.otowave.api.routes.images.models.ItemModel;
import github.otowave.api.routes.common.services.items.ItemAlbum;
import github.otowave.api.routes.common.services.items.ItemMusic;
import github.otowave.api.routes.common.services.items.ItemPlaylist;
import github.otowave.api.routes.common.services.items.ItemUser;

public class ItemFactoryImp implements ItemFactory {
    @Override
    public Item makeItem(ItemModel itemModel) throws InvalidItemTypeException {
        String itemType = itemModel.itemType();
        return switch (itemType) {
            case "music" -> new ItemMusic(itemModel);
            case "playlist" -> new ItemPlaylist(itemModel);
            case "album" -> new ItemAlbum(itemModel);
            case "user" -> new ItemUser(itemModel);
            default -> throw new InvalidItemTypeException(String.format("Invalid item type: [%s]", itemType));
        };
    }
}