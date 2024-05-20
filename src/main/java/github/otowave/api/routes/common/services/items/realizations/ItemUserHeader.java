package github.otowave.api.routes.common.services.items.realizations;

import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class ItemUserHeader extends Item {
    @Autowired
    UsersProfileRepo usersProfileRepo;

    public ItemUserHeader(ItemModel itemModel, DefaultImageIDs defaultImageID) {
        super(itemModel, defaultImageID);
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemEntity()
                .flatMap(userProfileEntity -> {
                    userProfileEntity.setHeaderID(newImageID);
                    return usersProfileRepo.save(userProfileEntity);
                }).then();
    }

    @Override
    public Mono<UsersProfileEntity> getItemEntity() {
        return usersProfileRepo.findById(itemID);
    }
}
