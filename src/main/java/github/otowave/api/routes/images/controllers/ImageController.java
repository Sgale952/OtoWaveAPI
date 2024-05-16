package github.otowave.api.routes.images.controllers;

import github.otowave.api.routes.images.models.ItemModel;
import github.otowave.api.routes.images.entities.ImagesEntity;
import github.otowave.api.routes.images.services.ImageUploader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static github.otowave.api.security.Verifier.verify;
@RestController
@RequestMapping("/images/{itemType}/{itemID}")
public class ImageController {
    @PostMapping("/change-image")
    public void changeImage(@PathVariable String itemType, @PathVariable int itemID, @RequestParam boolean animated,
                            @RequestPart Mono<FilePart> image, @CookieValue String authToken) {
        int userID = verify(authToken, itemID);
        ItemModel item = new ItemModel(itemType, itemID);
        ImagesEntity imageEntity = new ImagesEntity(userID, animated);
        ImageUploader imageUploader = new ImageUploader(item, imageEntity, image);
    }
}
