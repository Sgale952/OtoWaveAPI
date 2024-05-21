package github.otowave.api.routes.common.services.items.products;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.music.entities.MusicEntity;
import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.music.repositories.MusicMetaRepo;
import github.otowave.api.routes.music.repositories.MusicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class ItemMusic extends Item {
    @Autowired
    MusicRepo musicRepo;
    @Autowired
    MusicMetaRepo musicMetaRepo;

    public ItemMusic(ItemModel itemModel, DefaultImageIDs defaultImageID) {
        super(itemModel, defaultImageID);
    }

    @Override
    public Mono<Void> profile() {
        return null;
    }

    @Override
    public Mono<Void> changeName(String newName) {
        return getItemEntity()
                .flatMap(entity -> {
                    entity.setTitle(newName);
                    return musicRepo.save(entity)
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
    public Mono<Void> delete() {
        return getItemEntity().flatMap(entity -> musicRepo.delete(entity));
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemEntity()
                .flatMap(entity -> {
                    entity.setCoverID(newImageID);
                    return musicRepo.save(entity);
                }).then();
    }

    @Override
    public Mono<MusicEntity> getItemEntity() {
        return musicRepo.findById(itemID);
    }

    @Override
    public Mono<MusicMetaEntity> getItemMetaEntity() {
        return musicMetaRepo.findById(itemID);
    }
}
