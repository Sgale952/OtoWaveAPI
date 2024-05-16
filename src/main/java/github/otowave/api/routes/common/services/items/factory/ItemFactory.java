package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.exceptions.InvalidItemTypeException;
import github.otowave.api.routes.images.models.ItemModel;

public interface ItemFactory {
    Item makeItem(ItemModel itemModel) throws InvalidItemTypeException;
}
