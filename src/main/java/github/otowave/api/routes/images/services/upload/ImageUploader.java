package github.otowave.api.routes.images.services.upload;

import github.otowave.api.routes.common.models.items.ItemModel;
import github.otowave.api.routes.images.services.upload.apply.ImageApplier;
import github.otowave.api.routes.images.services.upload.save.ImageSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ImageUploader {
    @Autowired
    ImageSaver imageSaver;
    @Autowired
    ImageApplier imageApplier;

    public ImageUploader() {
    }

    public Mono<Integer> uploadImage(Mono<FilePart> imageFile, ItemModel itemModel) {
        return imageSaver.saveImage(imageFile)
                .flatMap(imageEntity -> imageApplier.applyImageToItem(itemModel, Mono.just(imageEntity)));
    }
}