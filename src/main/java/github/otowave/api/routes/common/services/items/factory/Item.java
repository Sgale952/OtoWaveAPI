package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.routes.common.services.items.Customizable;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.common.models.ItemModel;
import reactor.core.publisher.Mono;

public abstract class Item implements Customizable {
    protected int itemID;
    protected int defaultImageID;

    public Item(ItemModel itemModel, DefaultImageIDs defaultImageID) {
        this.itemID = itemModel.itemID();
        this.defaultImageID = defaultImageID.getImageID();
    }

    public abstract Mono<Void> changeImage(int newImageID);

    public Mono<Void> resetImage() {
        return changeImage(defaultImageID);
    }

    public abstract Mono getItemEntity();

    public abstract Mono getItemMetaEntity();
}
