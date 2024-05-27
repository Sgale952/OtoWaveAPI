package github.otowave.api.routes.common.services.items.products.user;

import github.otowave.api.routes.common.models.items.ItemModel;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.users.entities.UsersMetaEntity;
import github.otowave.api.routes.users.entities.UsersSecurityEntity;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.repositories.UsersMetaRepo;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import github.otowave.api.routes.users.repositories.UsersSecurityRepo;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.images.models.DefaultImageIDs.USER;

public class ItemUser extends Item {
    protected final UsersSecurityRepo usersSecurityRepo;
    protected final UsersMetaRepo usersMetaRepo;
    protected final UsersProfileRepo usersProfileRepo;

    public ItemUser(ItemModel itemModel, UsersSecurityRepo usersSecurityRepo, UsersMetaRepo usersMetaRepo, UsersProfileRepo usersProfileRepo) {
        super(itemModel, USER);
        this.usersSecurityRepo = usersSecurityRepo;
        this.usersMetaRepo = usersMetaRepo;
        this.usersProfileRepo = usersProfileRepo;
    }

    public ItemUser(DefaultImageIDs defaultImageID, ItemModel itemModel, UsersSecurityRepo usersSecurityRepo, UsersMetaRepo usersMetaRepo, UsersProfileRepo usersProfileRepo) {
        super(itemModel, defaultImageID);
        this.usersSecurityRepo = usersSecurityRepo;
        this.usersMetaRepo = usersMetaRepo;
        this.usersProfileRepo = usersProfileRepo;
    }

    @Override
    public Mono profile() {
        return null;
    }

    @Override
    public Mono<Void> changeName(String newName) {
        return getItemProfileEntity()
                .flatMap(entity -> {
                    entity.setNickname(newName);
                    return usersProfileRepo.save(entity)
                            .then();
                });
    }

    @Override
    public Mono<Void> changeTale(String newTale) {
        return getItemMetaEntity()
                .flatMap(entity -> {
                    entity.setTale(newTale);
                    return usersMetaRepo.save(entity)
                            .then();
                });
    }

    @Override
    public Mono<Void> delete() {
        return getItemEntity().flatMap(entity -> usersSecurityRepo.delete(entity));
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemProfileEntity()
                .flatMap(entity -> {
                    entity.setAvatarID(newImageID);
                    return usersProfileRepo.save(entity);
                }).then();
    }

    @Override
    public Mono<Integer> getCurrentImageID() {
        return getItemProfileEntity().map(UsersProfileEntity::getAvatarID);
    }

    @Override
    public Mono<UsersSecurityEntity> getItemEntity() {
        return usersSecurityRepo.findById(itemID);
    }

    @Override
    public Mono<UsersMetaEntity> getItemMetaEntity() {
        return usersMetaRepo.findById(itemID);
    }

    public Mono<UsersProfileEntity> getItemProfileEntity() {
        return usersProfileRepo.findById(itemID);
    }
}
