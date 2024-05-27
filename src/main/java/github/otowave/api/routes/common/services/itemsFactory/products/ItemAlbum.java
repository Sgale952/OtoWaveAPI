package github.otowave.api.routes.common.services.itemsFactory.products;

import github.otowave.api.routes.albums.entities.AlbumsEntity;
import github.otowave.api.routes.albums.entities.AlbumsMetaEntity;
import github.otowave.api.routes.albums.repositories.AlbumsMetaRepo;
import github.otowave.api.routes.albums.repositories.AlbumsRepo;
import github.otowave.api.routes.common.services.itemsFactory.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ItemAlbum extends Item {
    private final AlbumsRepo albumsRepo;
    private final AlbumsMetaRepo albumsMetaRepo;

    @Autowired
    public ItemAlbum(AlbumsRepo albumsRepo, AlbumsMetaRepo albumsMetaRepo) {
        super(DefaultImageIDs.ALBUM);
        this.albumsRepo = albumsRepo;
        this.albumsMetaRepo = albumsMetaRepo;
    }

    @Override
    public Mono profile() {
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
    public Mono<Integer> getCurrentImageID() {
        return getItemEntity().map(AlbumsEntity::getCoverID);
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
