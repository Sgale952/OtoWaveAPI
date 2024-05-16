package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.routes.images.models.ItemModel;

public abstract class Item {
    protected int itemID;

    public Item(ItemModel itemModel) {
        this.itemID = itemModel.itemID();
    }

    public abstract void resetImage();

    public abstract void changeImage(int newImageID);
}
