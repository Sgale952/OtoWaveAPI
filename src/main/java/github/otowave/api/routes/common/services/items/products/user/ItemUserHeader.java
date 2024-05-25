package github.otowave.api.routes.common.services.items.products.user;

import github.otowave.api.routes.common.models.items.ItemModel;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import github.otowave.api.routes.users.repositories.UsersRepo;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.images.models.DefaultImageIDs.USER_HEADER;

public class ItemUserHeader extends ItemUser {
    public ItemUserHeader(ItemModel itemModel, UsersRepo usersRepo, UsersProfileRepo usersProfileRepo) {
        super(USER_HEADER, itemModel, usersRepo, usersProfileRepo);
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
