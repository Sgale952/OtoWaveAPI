package github.otowave.api.routes.common.services.items.products.user;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import reactor.core.publisher.Mono;

public class ItemUserHeader extends ItemUser {
    public ItemUserHeader(ItemModel itemModel, DefaultImageIDs defaultImageID) {
        super(itemModel, defaultImageID);
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemMetaEntity()
                .flatMap(metaEntity -> {
                    metaEntity.setHeaderID(newImageID);
                    return usersProfileRepo.save(metaEntity);
                }).then();
    }
}
