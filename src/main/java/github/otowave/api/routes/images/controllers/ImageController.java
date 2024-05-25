package github.otowave.api.routes.images.controllers;

import github.otowave.api.routes.common.models.items.ItemModel;
import github.otowave.api.routes.common.models.items.ItemTypes;
import github.otowave.api.routes.images.entities.ImagesEntity;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.images.services.upload.ImageUploader;
import github.otowave.api.routes.images.services.upload.apply.ImageApplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/images/{itemType}/{itemID}")
public class ImageController {
    @Autowired
    ImageUploader imageUploader;
    @Autowired
    ImageApplier imageApplier;

    @PostMapping("/change-image")
    public Mono<Integer> changeImage(@PathVariable String itemType, @PathVariable int itemID,
                                  @RequestPart Mono<FilePart> imageFile) {
        return imageFile.flatMap(file -> {
            ItemModel itemModel = new ItemModel(ItemTypes.valueOf(itemType.toUpperCase()), itemID);
            return imageUploader.uploadImage(imageFile, itemModel);
        });
    }

    @PostMapping("/reset-image")
    public Mono<Integer> resetImage(@PathVariable String itemType, @PathVariable int itemID) {
        ItemModel itemModel = new ItemModel(ItemTypes.valueOf(itemType.toUpperCase()), itemID);
        Mono<ImagesEntity> imagesEntity = Mono.just(new ImagesEntity(DefaultImageIDs.valueOf(itemType.toUpperCase())));
        return imageApplier.applyImageToItem(itemModel, imagesEntity);
    }
}