package github.otowave.api.routes.common.services.items.products;

import github.otowave.api.routes.albums.entities.AlbumsEntity;
import github.otowave.api.routes.albums.entities.AlbumsMetaEntity;
import github.otowave.api.routes.albums.repositories.AlbumsMetaRepo;
import github.otowave.api.routes.albums.repositories.AlbumsRepo;
import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.services.items.factory.Item;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.images.models.DefaultImageIDs.ALBUM_COVER;

public class ItemAlbum extends Item {
    private final AlbumsRepo albumsRepo;
    private final AlbumsMetaRepo albumsMetaRepo;
    
    public ItemAlbum(ItemModel itemModel, AlbumsRepo albumsRepo, AlbumsMetaRepo albumsMetaRepo) {
        super(itemModel, ALBUM_COVER);
        this.albumsRepo = albumsRepo;
        this.albumsMetaRepo = albumsMetaRepo;
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
