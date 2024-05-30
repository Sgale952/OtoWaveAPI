package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.routes.common.models.items.ItemModel;
import github.otowave.api.routes.common.models.items.ItemTypes;
import github.otowave.api.routes.common.services.items.products.*;
import github.otowave.api.routes.common.services.items.products.songlist.ItemAlbum;
import github.otowave.api.routes.common.services.items.products.songlist.ItemPlaylist;
import github.otowave.api.routes.common.services.items.products.user.ItemUser;
import github.otowave.api.routes.common.services.items.products.user.ItemUserHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ItemFactoryImp implements ItemFactory {
    private final ApplicationContext context;

    @Autowired
    public ItemFactoryImp(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Mono<Item> makeItem(ItemTypes itemType, int itemID) {
        ItemModel itemModel = new ItemModel(itemType, itemID);
        return makeItem(itemModel);
    }

    @Override
    public Mono<Item> makeItem(ItemModel itemModel) {
        ItemTypes itemType = itemModel.itemType();
        return Mono.defer(() -> {
            switch (itemType) {
                case MUSIC -> {
                    return getItem(ItemMusic.class, itemModel);
                }
                case PLAYLISTS -> {
                    return getItem(ItemPlaylist.class, itemModel);
                }
                case ALBUMS -> {
                    return getItem(ItemAlbum.class, itemModel);
                }
                case USERS -> {
                    return getItem("ItemUser", ItemUser.class, itemModel);
                }
                case USER_HEADER -> {
                    return getItem("ItemUserHeader", ItemUserHeader.class, itemModel);
                }
                default -> {
                    return Mono.error(new IllegalArgumentException("Unknown item type: " + itemType));
                }
            }
        });
    }

    private <T extends Item> Mono<Item> getItem(Class<T> itemClass, ItemModel itemModel) {
        Item item = context.getBean(itemClass);
        return prepareItem(item, itemModel);
    }

    private <T extends Item> Mono<Item> getItem(String beanName, Class<T> itemClass, ItemModel itemModel) {
        Item item = context.getBean(beanName, itemClass);
        return prepareItem(item, itemModel);
    }

    private Mono<Item> prepareItem(Item item, ItemModel itemModel) {
        item.setItemID(itemModel.itemID());
        return Mono.just(item);
    }
}