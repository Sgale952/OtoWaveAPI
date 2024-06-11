package github.otowave.api.routes.common.services.items.products.user;

import github.otowave.api.routes.common.services.items.factory.item.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.users.entities.UsersMetaEntity;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.entities.UsersSecurityEntity;
import github.otowave.api.routes.users.models.UserProfileModel;
import github.otowave.api.routes.users.repositories.UsersMetaRepo;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import github.otowave.api.routes.users.repositories.UsersSecurityRepo;
import github.otowave.api.routes.users.services.UserProfileMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("ItemUser")
public class ItemUser extends Item {
    protected final UsersProfileRepo usersProfileRepo;
    protected final UsersMetaRepo usersMetaRepo;
    protected final UsersSecurityRepo usersSecurityRepo;
    @Autowired
    UserProfileMaker userProfileMaker;

    @Autowired
    public ItemUser(UsersProfileRepo usersProfileRepo, UsersMetaRepo usersMetaRepo, UsersSecurityRepo usersSecurityRepo) {
        super(DefaultImageIDs.USERS);
        this.usersProfileRepo = usersProfileRepo;
        this.usersSecurityRepo = usersSecurityRepo;
        this.usersMetaRepo = usersMetaRepo;
    }

    public ItemUser(DefaultImageIDs defaultImageID, UsersProfileRepo usersProfileRepo, UsersMetaRepo usersMetaRepo, UsersSecurityRepo usersSecurityRepo) {
        super(defaultImageID);
        this.usersProfileRepo = usersProfileRepo;
        this.usersMetaRepo = usersMetaRepo;
        this.usersSecurityRepo = usersSecurityRepo;
    }

    @Override
    public Mono<UserProfileModel> profile() {
        return userProfileMaker.getProfile(getItemMetaEntity());
    }

    @Override
    public Mono<Integer> changeName(String newName) {
        return usersProfileRepo.changeName(itemID, newName).map(UsersProfileEntity::getItemID);
    }

    @Override
    public Mono<Integer> changeTale(String newTale) {
        return usersMetaRepo.changeTale(itemID, newTale).map(UsersMetaEntity::getItemID);
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemProfileEntity()
                .flatMap(entity -> {
                    entity.setCoverID(newImageID);
                    return usersProfileRepo.save(entity);
                }).then();
    }

    @Override
    public Mono<Integer> delete() {
        return getItemSecurityEntity().flatMap(entity -> usersSecurityRepo.delete(entity)).thenReturn(1);
    }

    @Override
    public Mono<Integer> getCurrentImageID() {
        return getItemProfileEntity().map(UsersProfileEntity::getCoverID);
    }

    @Override
    public Mono<UsersProfileEntity> getItemProfileEntity() {
        return usersProfileRepo.findById(itemID);
    }

    @Override
    public Mono<UsersMetaEntity> getItemMetaEntity() {
        return usersMetaRepo.findAllByItemID(itemID);
    }

    public Mono<UsersSecurityEntity> getItemSecurityEntity() {
        return usersSecurityRepo.findByItemID(itemID);
    }
}
