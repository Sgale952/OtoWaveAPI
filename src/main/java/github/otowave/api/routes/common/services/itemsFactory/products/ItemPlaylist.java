package github.otowave.api.routes.common.services.itemsFactory.products;

import github.otowave.api.routes.common.services.itemsFactory.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.playlists.entities.PlaylistsProfileEntity;
import github.otowave.api.routes.playlists.entities.PlaylistsMetaEntity;
import github.otowave.api.routes.playlists.repositories.PlaylistsMetaRepo;
import github.otowave.api.routes.playlists.repositories.PlaylistsProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ItemPlaylist extends Item {
    private final PlaylistsProfileRepo playlistsProfileRepo;
    private final PlaylistsMetaRepo playlistsMetaRepo;

    @Autowired
    public ItemPlaylist(PlaylistsProfileRepo playlistsProfileRepo, PlaylistsMetaRepo playlistsMetaRepo) {
        super(DefaultImageIDs.PLAYLIST);
        this.playlistsProfileRepo = playlistsProfileRepo;
        this.playlistsMetaRepo = playlistsMetaRepo;
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
        return getItemEntity().flatMap(entity -> playlistsProfileRepo.delete(entity));
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemEntity()
                .flatMap(entity -> {
                    entity.setCoverID(newImageID);
                    return playlistsProfileRepo.save(entity);
                }).then();
    }

    @Override
    public Mono<Integer> getCurrentImageID() {
        return getItemEntity().map(PlaylistsProfileEntity::getCoverID);
    }

    @Override
    public Mono<PlaylistsProfileEntity> getItemEntity() {
        return playlistsProfileRepo.findById(itemID);
    }

    @Override
    public Mono<PlaylistsMetaEntity> getItemMetaEntity() {
        return playlistsMetaRepo.findById(itemID);
    }
}
