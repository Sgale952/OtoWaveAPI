package github.otowave.api.routes.images.controllers;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.models.ItemTypes;
import github.otowave.api.routes.images.services.upload.ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/images/{itemType}/{itemID}")
public class ImageController {
    @Autowired
    ImageUploader imageUploader;

    @PostMapping("/change-image")
    public Mono<Void> changeImage(@PathVariable String itemType, @PathVariable int itemID,
                                  @RequestPart Mono<FilePart> imageFile, @CookieValue String authToken) {
        return imageFile.flatMap(file -> {
            ItemModel itemModel = new ItemModel(ItemTypes.valueOf(itemType), itemID, authToken);
            return imageUploader.uploadImage(itemModel, imageFile);
        });
    }
}
