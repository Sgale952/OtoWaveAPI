package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.models.ItemTypes;
import github.otowave.api.routes.common.services.items.products.*;
import github.otowave.api.routes.common.services.items.products.user.ItemUser;
import github.otowave.api.routes.common.services.items.products.user.ItemUserHeader;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import reactor.core.publisher.Mono;

import static github.otowave.api.security.Verifier.verify;

public class ItemFactoryImp implements ItemFactory {
    @Override
    public Mono<Item> makeOpenItem(ItemTypes itemType, int itemID) {
        ItemModel itemModel = new ItemModel(itemType, itemID);
        return makeItem(itemModel);
    }

    @Override
    public Mono<Item> makeCloseItem(ItemTypes itemType, int itemID, String authToken) {
        int userID = verify(authToken, itemID);
        ItemModel itemModel = new ItemModel(itemType, itemID, userID);
        return makeItem(itemModel);
    }

    @Override
    public Mono<Item> makeItem(ItemModel itemModel) {
        ItemTypes itemType = itemModel.itemType();
        return switch (itemType) {
            case MUSIC -> Mono.just(new ItemMusic(itemModel, DefaultImageIDs.MUSIC_COVER));
            case PLAYLIST -> Mono.just(new ItemPlaylist(itemModel, DefaultImageIDs.PLAYLIST_COVER));
            case ALBUM -> Mono.just(new ItemAlbum(itemModel, DefaultImageIDs.ALBUM_COVER));
            case USER -> Mono.just(new ItemUser(itemModel, DefaultImageIDs.USER_AVATAR));
            case USER_HEADER -> Mono.just(new ItemUserHeader(itemModel, DefaultImageIDs.USER_HEADER));
        };
    }
}