package github.otowave.api.routes.common.services.itemsFactory.products.user;

import github.otowave.api.routes.common.services.itemsFactory.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.users.entities.UsersMetaEntity;
import github.otowave.api.routes.users.entities.UsersSecurityEntity;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.repositories.UsersMetaRepo;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import github.otowave.api.routes.users.repositories.UsersSecurityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("ItemUser")
public class ItemUser extends Item {
    protected final UsersSecurityRepo usersSecurityRepo;
    protected final UsersProfileRepo usersProfileRepo;
    protected final UsersMetaRepo usersMetaRepo;

    @Autowired
    public ItemUser(UsersSecurityRepo usersSecurityRepo, UsersProfileRepo usersProfileRepo, UsersMetaRepo usersMetaRepo) {
        super(DefaultImageIDs.USER);
        this.usersSecurityRepo = usersSecurityRepo;
        this.usersProfileRepo = usersProfileRepo;
        this.usersMetaRepo = usersMetaRepo;
    }

    public ItemUser(DefaultImageIDs defaultImageID, UsersSecurityRepo usersSecurityRepo, UsersProfileRepo usersProfileRepo, UsersMetaRepo usersMetaRepo) {
        super(defaultImageID);
        this.usersSecurityRepo = usersSecurityRepo;
        this.usersProfileRepo = usersProfileRepo;
        this.usersMetaRepo = usersMetaRepo;
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

    public Mono<UsersProfileEntity> getItemProfileEntity() {
        return usersProfileRepo.findById(itemID);
    }

    @Override
    public Mono<UsersMetaEntity> getItemMetaEntity() {
        return usersMetaRepo.findById(itemID);
    }
}
