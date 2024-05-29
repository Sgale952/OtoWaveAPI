package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.routes.images.models.DefaultImageIDs;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public abstract class Item implements Customizable, Imageable {
    protected int itemID;
    protected final int defaultImageID;

    public Item(DefaultImageIDs defaultImageID) {
        this.defaultImageID = defaultImageID.getImageID();
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    protected abstract Mono<?> getItemProfileEntity();

    protected abstract Mono<?> getItemMetaEntity();
}
