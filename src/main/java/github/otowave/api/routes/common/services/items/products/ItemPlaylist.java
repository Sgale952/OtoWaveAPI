package github.otowave.api.routes.common.services.items.products;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.playlists.entities.PlaylistsEntity;
import github.otowave.api.routes.playlists.entities.PlaylistsMetaEntity;
import github.otowave.api.routes.playlists.repositories.PlaylistsMetaRepo;
import github.otowave.api.routes.playlists.repositories.PlaylistsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class ItemPlaylist extends Item {
    @Autowired
    PlaylistsRepo playlistsRepo;
    @Autowired
    PlaylistsMetaRepo playlistsMetaRepo;
    
    public ItemPlaylist(ItemModel itemModel, DefaultImageIDs defaultImageID) {
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
    public Mono<PlaylistsEntity> getItemEntity() {
        return playlistsRepo.findById(itemID);
    }

    @Override
    public Mono<PlaylistsMetaEntity> getItemMetaEntity() {
        return playlistsMetaRepo.findById(itemID);
    }
}
