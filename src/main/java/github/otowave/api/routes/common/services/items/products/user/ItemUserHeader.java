package github.otowave.api.routes.common.services.items.products.user;

import github.otowave.api.routes.common.models.items.ItemModel;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import github.otowave.api.routes.users.repositories.UsersRepo;
import reactor.core.publisher.Mono;

public class ItemUserHeader extends ItemUser {
    public ItemUserHeader(ItemModel itemModel, UsersRepo usersRepo, UsersProfileRepo usersProfileRepo) {
        super(itemModel, usersRepo, usersProfileRepo);
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemMetaEntity()
                .flatMap(metaEntity -> {
                    metaEntity.setHeaderID(newImageID);
                    return usersProfileRepo.save(metaEntity);
                }).then();
    }

    @Override
    public Mono<Integer> getCurrentImageID() {
        return getItemMetaEntity().map(UsersProfileEntity::getHeaderID);
    }
}
