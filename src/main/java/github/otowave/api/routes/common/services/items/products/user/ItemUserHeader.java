package github.otowave.api.routes.common.services.items.products.user;

import github.otowave.api.routes.common.models.items.ItemModel;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.repositories.UsersMetaRepo;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import github.otowave.api.routes.users.repositories.UsersSecurityRepo;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.images.models.DefaultImageIDs.USER_HEADER;

public class ItemUserHeader extends ItemUser {
    public ItemUserHeader(ItemModel itemModel, UsersSecurityRepo usersSecurityRepo, UsersMetaRepo usersMetaRepo, UsersProfileRepo usersProfileRepo) {
        super(USER_HEADER, itemModel, usersSecurityRepo, usersMetaRepo, usersProfileRepo);
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemProfileEntity()
                .flatMap(entity -> {
                    entity.setHeaderID(newImageID);
                    return usersProfileRepo.save(entity);
                }).then();
    }

    @Override
    public Mono<Integer> getCurrentImageID() {
        return getItemProfileEntity().map(UsersProfileEntity::getHeaderID);
    }
}
