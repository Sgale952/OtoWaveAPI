package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.models.ItemTypes;
import github.otowave.api.routes.common.services.items.products.*;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import reactor.core.publisher.Mono;

public class ItemFactoryImp implements ItemFactory {
    @Override
    public Mono<Item> makeItem(ItemModel itemModel) {
        ItemTypes itemType = itemModel.itemType();
        return switch (itemType) {
            case MUSIC -> Mono.just(new ItemMusic(itemModel, DefaultImageIDs.MUSIC_COVER));
            case PLAYLIST -> Mono.just(new ItemPlaylist(itemModel, DefaultImageIDs.PLAYLIST_COVER));
            case ALBUM -> Mono.just(new ItemAlbum(itemModel, DefaultImageIDs.ALBUM_COVER));
            case USER_AVATAR -> Mono.just(new ItemUserAvatar(itemModel, DefaultImageIDs.USER_AVATAR));
            case USER_HEADER -> Mono.just(new ItemUserHeader(itemModel, DefaultImageIDs.USER_HEADER));
        };
    }
}