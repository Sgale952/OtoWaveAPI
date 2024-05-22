package github.otowave.api.routes.images.services;

import github.otowave.api.routes.common.models.ItemModel;
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
    @Autowired
    ImageConverter imageConverter;

    public ImageUploader() {
    }
    //TODO: Добавить логику удаления прошлого изображения
    public Mono<Void> uploadImage(ItemModel itemModel, Mono<FilePart> imageFile) {
        return imageSaver.saveImage(itemModel, imageFile)
                .flatMap(imageEntity -> imageApplier.applyImageToItem(itemModel, Mono.just(imageEntity))
                        .then(imageConverter.convertImageFileToWebp(imageEntity.getImageID(), imageFile))
                        .then());
    }
}
