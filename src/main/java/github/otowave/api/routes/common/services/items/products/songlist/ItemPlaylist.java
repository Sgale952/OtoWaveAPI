package github.otowave.api.routes.common.services.items.products.songlist;

import github.otowave.api.routes.common.models.ProfileModel;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsProfileEntity;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsMetaEntity;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsMetaRepo;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsProfileRepo;
import github.otowave.api.routes.songlists.services.playlists.PlaylistsProfileMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ItemPlaylist extends Item {
    private final PlaylistsProfileRepo playlistsProfileRepo;
    private final PlaylistsMetaRepo playlistsMetaRepo;
    @Autowired
    PlaylistsProfileMaker playlistsProfileMaker;

    @Autowired
    public ItemPlaylist(PlaylistsProfileRepo playlistsProfileRepo, PlaylistsMetaRepo playlistsMetaRepo) {
        super(DefaultImageIDs.PLAYLIST);
        this.playlistsProfileRepo = playlistsProfileRepo;
        this.playlistsMetaRepo = playlistsMetaRepo;
    }

    @Override
    public Mono<ProfileModel> profile() {
        return playlistsProfileMaker.getProfile(getItemMetaEntity());
    }

    @Override
    public Mono<Void> changeName(String newName) {
        return getItemProfileEntity()
                .flatMap(entity -> {
                    entity.setTitle(newName);
                    return playlistsProfileRepo.save(entity)
                            .then();
                });
    }

    @Override
    public Mono<Void> changeTale(String newTale) {
        return getItemMetaEntity()
                .flatMap(metaEntity -> {
                    metaEntity.setTale(newTale);
                    return playlistsMetaRepo.save(metaEntity)
                            .then();
                });
    }

    @Override
    public Mono<Void> delete() {
        return getItemProfileEntity().flatMap(entity -> playlistsProfileRepo.delete(entity));
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemProfileEntity()
                .flatMap(entity -> {
                    entity.setCoverID(newImageID);
                    return playlistsProfileRepo.save(entity);
                }).then();
    }

    @Override
    public Mono<Integer> getCurrentImageID() {
        return getItemProfileEntity().map(PlaylistsProfileEntity::getCoverID);
    }

    @Override
    public Mono<PlaylistsProfileEntity> getItemProfileEntity() {
        return playlistsProfileRepo.findById(itemID);
    }

    @Override
    public Mono<PlaylistsMetaEntity> getItemMetaEntity() {
        return playlistsMetaRepo.findById(itemID);
    }
}
