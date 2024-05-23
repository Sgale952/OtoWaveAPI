package github.otowave.api.routes.common.services.items.products.user;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import github.otowave.api.routes.users.repositories.UsersRepo;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.images.models.DefaultImageIDs.USER_HEADER;

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
}
