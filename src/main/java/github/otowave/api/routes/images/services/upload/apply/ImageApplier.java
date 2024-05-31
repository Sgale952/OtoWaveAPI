package github.otowave.api.routes.images.services.upload.apply;

import github.otowave.api.routes.common.services.items.factory.item.Item;
import github.otowave.api.routes.common.services.items.factory.ItemFactoryImp;
import github.otowave.api.routes.images.entities.ImagesEntity;
import github.otowave.api.routes.common.models.items.ItemModel;
import github.otowave.api.routes.images.services.upload.UploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.common.models.items.ItemTypes.toItemType;
import static github.otowave.api.routes.images.models.DefaultImageIDs.toDefaultImageID;

@Service
public class ImageApplier {
    private final ItemFactoryImp itemFactory;
    @Autowired
    UploadHelper uploadHelper;
    @Autowired
    ImageDeleter imageDeleter;
    @Autowired
    public ImageApplier(ItemFactoryImp itemFactory) {
        this.itemFactory = itemFactory;
    }

    public Mono<Integer> resetImage(String itemType, int musicID) {
        ItemModel itemModel = new ItemModel(toItemType(itemType), musicID);
        Mono<ImagesEntity> imagesEntity = Mono.just(new ImagesEntity(toDefaultImageID(itemType)));
        return applyImageToItem(itemModel, imagesEntity);
    }

    public Mono<Integer> applyImageToItem(ItemModel itemModel, Mono<ImagesEntity> imageEntity) {
        return imageEntity.flatMap(entity -> {
            Mono<Item> item = getItemFromModel(itemModel);
            return imageDeleter.deletePastImage(item)
                    .then(uploadHelper.getImageID(entity))
                    .flatMap(newImageID -> changeItemImage(item, newImageID));
        });
    }

    private Mono<Item> getItemFromModel(ItemModel itemModel) {
        return itemFactory.makeItem(itemModel);
    }

    private Mono<Integer> changeItemImage(Mono<Item> item, int newImageID) {
        return item.flatMap(i -> i.changeImage(newImageID)).thenReturn(newImageID);
    }
}