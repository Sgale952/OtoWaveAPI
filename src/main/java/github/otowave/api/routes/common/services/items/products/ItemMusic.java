package github.otowave.api.routes.common.services.items.products;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.music.entities.MusicEntity;
import github.otowave.api.routes.music.repositories.MusicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public class ItemMusic extends Item {
    @Autowired
    MusicRepo musicRepo;

    public ItemMusic(ItemModel itemModel, DefaultImageIDs defaultImageID) {
        super(itemModel, defaultImageID);
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemEntity()
                .flatMap(musicEntity -> {
                    musicEntity.setCoverID(newImageID);
                    return musicRepo.save(musicEntity);
                }).then();
    }

    @Override
    public Mono<MusicEntity> getItemEntity() {
        return musicRepo.findById(itemID);
    }
}
