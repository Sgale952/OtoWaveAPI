package github.otowave.api.routes.common.services.items.products;

import github.otowave.api.routes.albums.entities.AlbumsEntity;
import github.otowave.api.routes.albums.entities.AlbumsMetaEntity;
import github.otowave.api.routes.albums.repositories.AlbumsMetaRepo;
import github.otowave.api.routes.albums.repositories.AlbumsRepo;
import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class ItemAlbum extends Item {
    @Autowired
    AlbumsRepo albumsRepo;
    @Autowired
    AlbumsMetaRepo albumsMetaRepo;
    
    public ItemAlbum(ItemModel itemModel, DefaultImageIDs defaultImageID) {
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
                    return albumsRepo.save(entity)
                            .then();
                });
    }

    @Override
    public Mono<Void> changeTale(String newTale) {
        return getItemMetaEntity()
                .flatMap(metaEntity -> {
                    metaEntity.setTale(newTale);
                    return albumsMetaRepo.save(metaEntity)
                            .then();
                });
    }

    @Override
    public Mono<Void> delete() {
        return getItemEntity().flatMap(entity -> albumsRepo.delete(entity));
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemEntity()
                .flatMap(entity -> {
                    entity.setCoverID(newImageID);
                    return albumsRepo.save(entity);
                }).then();
    }

    @Override
    public Mono<AlbumsEntity> getItemEntity() {
        return albumsRepo.findById(itemID);
    }

    @Override
    public Mono<AlbumsMetaEntity> getItemMetaEntity() {
        return albumsMetaRepo.findById(itemID);
    }
}
