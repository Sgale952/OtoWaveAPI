package github.otowave.api.routes.common.services.items.products;

import github.otowave.api.routes.albums.entities.AlbumsEntity;
import github.otowave.api.routes.albums.repositories.AlbumsRepo;
import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class ItemAlbum extends Item {
    @Autowired
    AlbumsRepo albumsRepo;
    
    public ItemAlbum(ItemModel itemModel, DefaultImageIDs defaultImageID) {
        super(itemModel, defaultImageID);
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemEntity()
                .flatMap(albumEntity -> {
                    albumEntity.setCoverID(newImageID);
                    return albumsRepo.save(albumEntity);
                }).then();
    }

    @Override
    public Mono<AlbumsEntity> getItemEntity() {
        return albumsRepo.findById(itemID);
    }
}
