package github.otowave.api.routes.images.controllers;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.common.models.ItemTypes;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.common.services.items.factory.ItemFactoryImp;
import github.otowave.api.routes.images.services.ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/images/{itemType}/{itemID}")
public class ImageController {
    private final ItemFactoryImp itemFactory;
    @Autowired
    ImageUploader imageUploader;
    @Autowired
    public ImageController(ItemFactoryImp itemFactory) {
        this.itemFactory = itemFactory;
    }

    @PostMapping("/reset-image")
    public Mono<Void> resetImage(@PathVariable String itemType, @PathVariable int itemID,
                                  @CookieValue String authToken) {
        return itemFactory.makeItem(ItemTypes.valueOf(itemType), itemID).flatMap(Item::resetImage);
    }

    @PostMapping("/change-image")
    public Mono<Void> changeImage(@PathVariable String itemType, @PathVariable int itemID,
                                  @RequestPart Mono<FilePart> imageFile, @CookieValue String authToken) {
        return imageFile.flatMap(file -> {
            ItemModel itemModel = new ItemModel(ItemTypes.valueOf(itemType), itemID);
            return imageUploader.uploadImage(itemModel, imageFile);
        });
    }
}
