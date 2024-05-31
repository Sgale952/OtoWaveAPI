package github.otowave.api.routes.common.services.items.products;

import github.otowave.api.routes.common.services.items.factory.item.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.music.entities.MusicProfileEntity;
import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.music.models.MusicProfileModel;
import github.otowave.api.routes.music.repositories.MusicMetaRepo;
import github.otowave.api.routes.music.repositories.MusicProfileRepo;
import github.otowave.api.routes.music.services.MusicDeleter;
import github.otowave.api.routes.music.services.MusicProfileMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ItemMusic extends Item {
    private final MusicProfileRepo musicProfileRepo;
    private final MusicMetaRepo musicMetaRepo;
    @Autowired
    MusicProfileMaker musicProfileMaker;
    @Autowired
    MusicDeleter musicDeleter;

    @Autowired
    public ItemMusic(MusicProfileRepo musicProfileRepo, MusicMetaRepo musicMetaRepo) {
        super(DefaultImageIDs.MUSIC);
        this.musicProfileRepo = musicProfileRepo;
        this.musicMetaRepo = musicMetaRepo;
    }

    @Override
    public Mono<MusicProfileModel> profile() {
        return musicProfileMaker.getProfile(getItemMetaEntity());
    }

    @Override
    public Mono<Void> changeName(String newName) {
        return getItemProfileEntity()
                .flatMap(entity -> {
                    entity.setTitle(newName);
                    return musicProfileRepo.save(entity)
                            .then();
                });
    }

    @Override
    public Mono<Void> changeTale(String newTale) {
        return getItemMetaEntity()
                .flatMap(metaEntity -> {
                    metaEntity.setTale(newTale);
                    return musicMetaRepo.save(metaEntity)
                            .then();
                });
    }

    @Override
    public Mono<Integer> delete() {
        return musicDeleter.delete(itemID);
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemProfileEntity()
                .flatMap(entity -> {
                    entity.setCoverID(newImageID);
                    return musicProfileRepo.save(entity);
                }).then();
    }

    @Override
    public Mono<Integer> getCurrentImageID() {
        return getItemProfileEntity().map(MusicProfileEntity::getCoverID);
    }

    @Override
    public Mono<MusicProfileEntity> getItemProfileEntity() {
        return musicProfileRepo.findById(itemID);
    }

    @Override
    public Mono<MusicMetaEntity> getItemMetaEntity() {
        return musicMetaRepo.findAllByItemID(itemID);
    }
}
