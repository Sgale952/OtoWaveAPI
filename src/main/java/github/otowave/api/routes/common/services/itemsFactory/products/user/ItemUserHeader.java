package github.otowave.api.routes.common.services.itemsFactory.products.user;

import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.repositories.UsersMetaRepo;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import github.otowave.api.routes.users.repositories.UsersSecurityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("ItemUserHeader") //явное указание названи€ компонента, чтобы Spring мог отличить ItemUserHeader от ItemUser
public class ItemUserHeader extends ItemUser {
    @Autowired
    public ItemUserHeader(UsersSecurityRepo usersSecurityRepo, UsersProfileRepo usersProfileRepo, UsersMetaRepo usersMetaRepo) {
        super(DefaultImageIDs.USER_HEADER, usersSecurityRepo, usersProfileRepo, usersMetaRepo);
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
