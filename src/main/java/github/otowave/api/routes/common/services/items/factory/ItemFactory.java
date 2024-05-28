package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.routes.common.models.items.ItemModel;
import github.otowave.api.routes.common.models.items.ItemTypes;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface ItemFactory {
    Mono<Item> makeItem(ItemTypes itemType, int itemID);

    Mono<Item> makeItem(ItemModel itemModel);
}