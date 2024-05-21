package github.otowave.api.routes.common.services.items.products.user;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.users.entities.UsersEntity;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import github.otowave.api.routes.users.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class ItemUser extends Item {
    @Autowired
    UsersRepo usersRepo;
    @Autowired
    UsersProfileRepo usersProfileRepo;

    public ItemUser(ItemModel itemModel, DefaultImageIDs defaultImageID) {
        super(itemModel, defaultImageID);
    }

    @Override
    public Mono<Void> profile() {
        return null;
    }

    @Override
    public Mono<Void> changeName(String newName) {
        return getItemMetaEntity()
                .flatMap(entity -> {
                    entity.setNickname(newName);
                    return usersProfileRepo.save(entity)
                            .then();
                });
    }

    @Override
    public Mono<Void> changeTale(String newTale) {
        return getItemMetaEntity()
                .flatMap(metaEntity -> {
                    metaEntity.setTale(newTale);
                    return usersProfileRepo.save(metaEntity)
                            .then();
                });
    }

    @Override
    public Mono<Void> delete() {
        return getItemEntity().flatMap(entity -> usersRepo.delete(entity));
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemMetaEntity()
                .flatMap(metaEntity -> {
                    metaEntity.setAvatarID(newImageID);
                    return usersProfileRepo.save(metaEntity);
                }).then();
    }

    @Override
    public Mono<UsersEntity> getItemEntity() {
        return usersRepo.findById(itemID);
    }

    @Override
    public Mono<UsersProfileEntity> getItemMetaEntity() {
        return usersProfileRepo.findById(itemID);
    }
}