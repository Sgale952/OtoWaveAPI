package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.exceptions.InvalidItemTypeException;
import github.otowave.api.routes.common.models.ItemModel;
import reactor.core.publisher.Mono;

public interface ItemFactory {
    Mono<Item> makeItem(ItemModel itemModel) throws InvalidItemTypeException;
}
