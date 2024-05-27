package github.otowave.api.routes.common.services.itemsFactory.factory;

import github.otowave.api.routes.images.models.DefaultImageIDs;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public abstract class Item implements Customizable {
    protected int itemID;
    protected final int defaultImageID;

    public Item(DefaultImageIDs defaultImageID) {
        this.defaultImageID = defaultImageID.getImageID();
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public abstract Mono<Integer> getCurrentImageID();

    public abstract Mono<Void> changeImage(int newImageID);

    public abstract Mono getItemEntity();

    public abstract Mono getItemMetaEntity();
}
