package github.otowave.api.routes.common.services.items;

import github.otowave.api.routes.images.models.ItemModel;
import github.otowave.api.routes.common.services.items.factory.Item;

public class ItemUser extends Item {
    public ItemUser(ItemModel itemModel) {
        super(itemModel);
    }

    @Override
    public void changeImage(int newImageID) {

    }

    @Override
    public void resetImage() {

    }
}
