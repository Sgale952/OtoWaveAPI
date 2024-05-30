package github.otowave.api.routes.images.controllers;

import github.otowave.api.routes.common.models.items.ItemModel;
import github.otowave.api.routes.images.entities.ImagesEntity;
import github.otowave.api.routes.images.services.upload.ImageUploader;
import github.otowave.api.routes.images.services.upload.apply.ImageApplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static github.otowave.api.routes.common.models.items.ItemTypes.toItemType;
import static github.otowave.api.routes.images.models.DefaultImageIDs.toDefaultImageID;

@RestController
@RequestMapping("/images/{itemType}/{itemID}")
public class ImageController {
    @Autowired
    ImageUploader imageUploader;
    @Autowired
    ImageApplier imageApplier;

    @PostMapping("/change-image")
    Mono<Integer> changeImage(@PathVariable String itemType, @PathVariable int itemID,
                                  @RequestPart Mono<FilePart> imageFile) {
        return imageFile.flatMap(file -> {
            ItemModel itemModel = new ItemModel(toItemType(itemType), itemID);
            return imageUploader.upload(imageFile, itemModel);
        });
    }

    @PostMapping("/reset-image")
    Mono<Integer> resetImage(@PathVariable String itemType, @PathVariable int itemID) {
        ItemModel itemModel = new ItemModel(toItemType(itemType), itemID);
        Mono<ImagesEntity> imagesEntity = Mono.just(new ImagesEntity(toDefaultImageID(itemType)));
        return imageApplier.applyImageToItem(itemModel, imagesEntity);
    }
}