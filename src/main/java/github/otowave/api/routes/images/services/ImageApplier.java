package github.otowave.api.routes.images.services;

import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.common.services.items.factory.ItemFactoryImp;
import github.otowave.api.routes.images.entities.ImagesEntity;
import github.otowave.api.routes.common.models.ItemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ImageApplier {
    @Autowired
    ImageUploader imageUploader;

    public ImageApplier() {
    }

    void applyImageToItem(ItemModel itemModel, Mono<ImagesEntity> imageEntity) {
        imageEntity
                .flatMap(entity -> getUploadedImageID(entity)
                        .flatMap(newImageID -> changeItemImage(getItemFromModel(itemModel), newImageID)))
                .subscribe();
    }

    private Mono<Integer> getUploadedImageID(ImagesEntity imageEntity) {
        return Mono.just(imageEntity.getImageID());
    }

    private Mono<Item> getItemFromModel(ItemModel itemModel) {
        return new ItemFactoryImp().makeItem(itemModel);
    }

    private Mono<Void> changeItemImage(Mono<Item> item, int newImageID) {
        return item.flatMap(i -> i.changeImage(newImageID));
    }
}
