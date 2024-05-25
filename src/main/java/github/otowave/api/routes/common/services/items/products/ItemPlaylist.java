package github.otowave.api.routes.common.services.items.products;

import github.otowave.api.routes.common.models.items.ItemModel;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.playlists.entities.PlaylistsEntity;
import github.otowave.api.routes.playlists.entities.PlaylistsMetaEntity;
import github.otowave.api.routes.playlists.repositories.PlaylistsMetaRepo;
import github.otowave.api.routes.playlists.repositories.PlaylistsRepo;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.images.models.DefaultImageIDs.PLAYLIST;

public class ItemPlaylist extends Item {
    private final PlaylistsRepo playlistsRepo;
    private final PlaylistsMetaRepo playlistsMetaRepo;
    
    public ItemPlaylist(ItemModel itemModel, PlaylistsRepo playlistsRepo, PlaylistsMetaRepo playlistsMetaRepo) {
        super(itemModel, PLAYLIST);
        this.playlistsRepo = playlistsRepo;
        this.playlistsMetaRepo = playlistsMetaRepo;
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
                    return playlistsRepo.save(entity)
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
        return getItemEntity().flatMap(entity -> playlistsRepo.delete(entity));
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemEntity()
                .flatMap(entity -> {
                    entity.setCoverID(newImageID);
                    return playlistsRepo.save(entity);
                }).then();
    }

    @Override
    public Mono<Integer> getCurrentImageID() {
        return getItemEntity().map(PlaylistsEntity::getCoverID);
    }

    @Override
    public Mono<PlaylistsEntity> getItemEntity() {
        return playlistsRepo.findById(itemID);
    }

    @Override
    public Mono<PlaylistsMetaEntity> getItemMetaEntity() {
        return playlistsMetaRepo.findById(itemID);
    }
}
