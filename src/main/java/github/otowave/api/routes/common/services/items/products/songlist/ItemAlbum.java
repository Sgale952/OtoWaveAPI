package github.otowave.api.routes.common.services.items.products.songlist;

import github.otowave.api.routes.actions.services.songlists.UserAlbumsActions;
import github.otowave.api.routes.common.services.items.factory.songlist.SonglistItem;
import github.otowave.api.routes.songlists.entities.albums.AlbumsFillingEntity;
import github.otowave.api.routes.songlists.entities.albums.AlbumsProfileEntity;
import github.otowave.api.routes.songlists.entities.albums.AlbumsMetaEntity;
import github.otowave.api.routes.songlists.entities.albums.AlbumsSecurityEntity;
import github.otowave.api.routes.songlists.models.SonglistProfileModel;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsMetaRepo;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsProfileRepo;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.songlists.services.albums.AlbumDeleter;
import github.otowave.api.routes.songlists.services.albums.faces.AlbumsProfileMaker;
import github.otowave.api.routes.songlists.services.albums.AlbumsUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ItemAlbum extends SonglistItem {
    private final AlbumsProfileRepo albumsProfileRepo;
    private final AlbumsMetaRepo albumsMetaRepo;
    @Autowired
    AlbumsProfileMaker albumsProfileMaker;
    @Autowired
    AlbumsUploader albumsUploader;
    @Autowired
    AlbumDeleter albumDeleter;
    @Autowired
    UserAlbumsActions userAlbumsActions;

    @Autowired
    public ItemAlbum(AlbumsProfileRepo albumsProfileRepo, AlbumsMetaRepo albumsMetaRepo) {
        super(DefaultImageIDs.ALBUMS);
        this.albumsProfileRepo = albumsProfileRepo;
        this.albumsMetaRepo = albumsMetaRepo;
    }

    @Override
    public Mono<SonglistProfileModel> profile() {
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
    public Mono<Integer> delete() {
        return albumDeleter.delete(itemID);
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
    public Mono<Integer> upload(int creatorID, String title, String tale, boolean access) {
        return albumsUploader.upload(makeProfileEntity(creatorID, title), new AlbumsMetaEntity(tale), new AlbumsSecurityEntity(access));
    }

    @Override
    public AlbumsProfileEntity makeProfileEntity(int creatorID, String title) {
        return new AlbumsProfileEntity(creatorID, title);
    }

    @Override
    public Mono<Void> addMusic(int musicID) {
        return albumsUploader.addMusic(new AlbumsFillingEntity(itemID, musicID));
    }

    @Override
    public Mono<Void> removeMusic(int musicID) {
        return albumsUploader.removeMusic(itemID, musicID);
    }

    @Override
    public Mono<Void> like(int userID) {
        return userAlbumsActions.like(userID, itemID);
    }

    @Override
    public Mono<Void> discard(int userID) {
        return userAlbumsActions.discard(userID, itemID);
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
        return albumsMetaRepo.findByItemID(itemID);
    }
}
