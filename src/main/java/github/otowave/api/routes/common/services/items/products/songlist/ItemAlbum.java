package github.otowave.api.routes.common.services.items.products.songlist;

import github.otowave.api.routes.common.models.ProfileModel;
import github.otowave.api.routes.songlists.entities.albums.AlbumsProfileEntity;
import github.otowave.api.routes.songlists.entities.albums.AlbumsMetaEntity;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsMetaRepo;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsProfileRepo;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.songlists.services.albums.AlbumsProfileMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ItemAlbum extends Item {
    private final AlbumsProfileRepo albumsProfileRepo;
    private final AlbumsMetaRepo albumsMetaRepo;
    @Autowired
    AlbumsProfileMaker albumsProfileMaker;

    @Autowired
    public ItemAlbum(AlbumsProfileRepo albumsProfileRepo, AlbumsMetaRepo albumsMetaRepo) {
        super(DefaultImageIDs.ALBUM);
        this.albumsProfileRepo = albumsProfileRepo;
        this.albumsMetaRepo = albumsMetaRepo;
    }

    @Override
    public Mono<ProfileModel> profile() {
        return albumsProfileMaker.getProfile(getItemMetaEntity());
    }

    @Override
    public Mono<Void> changeName(String newName) {
        return getItemProfileEntity()
                .flatMap(entity -> {
                    entity.setTitle(newName);
                    return albumsProfileRepo.save(entity)
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
        return getItemProfileEntity().flatMap(entity -> albumsProfileRepo.delete(entity));
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemProfileEntity()
                .flatMap(entity -> {
                    entity.setCoverID(newImageID);
                    return albumsProfileRepo.save(entity);
                }).then();
    }

    @Override
    public Mono<Integer> getCurrentImageID() {
        return getItemProfileEntity().map(AlbumsProfileEntity::getCoverID);
    }

    @Override
    public Mono<AlbumsProfileEntity> getItemProfileEntity() {
        return albumsProfileRepo.findById(itemID);
    }

    @Override
    public Mono<AlbumsMetaEntity> getItemMetaEntity() {
        return albumsMetaRepo.findById(itemID);
    }
}
