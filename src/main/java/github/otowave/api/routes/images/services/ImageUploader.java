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
    /*
    1) сохранение изображения в бд -> получение сгенерированного id
    2) сохранене изображения на сервере (имя файла = imageID)
    3) конвертация изображения в webp (если !isAnimated())
    4) получение itemEntity, у которого меняется изображение
    5) удаление старого изображения
    6) замена imageID у itemEntity в бд
    */
    public Mono<Void> uploadImage(ItemModel itemModel, Mono<FilePart> imageFile) {
        return imageSaver.saveImage(imageFile)
                .flatMap(imageEntity -> imageApplier.applyImageToItem(itemModel, Mono.just(imageEntity))
                        .then(imageConverter.convertImageFileToWebp(imageEntity.getImageID(), imageFile))
                        .then());
    }
}
