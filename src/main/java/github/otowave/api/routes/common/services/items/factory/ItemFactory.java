package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.models.ItemTypes;
import reactor.core.publisher.Mono;

public interface ItemFactory {
    Mono<Item> makeOpenItem(ItemTypes itemType, int itemID);

    Mono<Item> makeCloseItem(ItemTypes itemType, int itemID, String authToken);

    Mono<Item> makeItem(ItemModel itemModel);
}