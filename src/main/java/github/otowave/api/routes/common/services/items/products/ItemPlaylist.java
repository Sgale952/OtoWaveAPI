package github.otowave.api.routes.common.services.items.products;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.playlists.entities.PlaylistsEntity;
import github.otowave.api.routes.playlists.repositories.PlaylistsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class ItemPlaylist extends Item {
    @Autowired
    PlaylistsRepo playlistsRepo;
    
    public ItemPlaylist(ItemModel itemModel, DefaultImageIDs defaultImageID) {
        super(itemModel, defaultImageID);
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemEntity()
                .flatMap(playlistEntity -> {
                    playlistEntity.setCoverID(newImageID);
                    return playlistsRepo.save(playlistEntity);
                }).then();
    }

    @Override
    public Mono<PlaylistsEntity> getItemEntity() {
        return playlistsRepo.findById(itemID);
    }
}
