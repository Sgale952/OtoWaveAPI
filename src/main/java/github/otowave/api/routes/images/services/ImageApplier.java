package github.otowave.api.routes.images.services;

import github.otowave.api.routes.images.entities.ImagesEntity;
import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.images.repositories.ImagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ImageApplier {
    @Autowired
    ImagesRepo imagesRepo;
    @Autowired
    ImageUploader imageUploader;

    public ImageApplier() {
    }

    void applyImageToItem(ItemModel itemModel, Mono<FilePart> imageFile) {
        imageFile
                .flatMap(file -> imageUploader.uploadImage(itemModel, imageFile))
                .flatMap(this::getUploadedImageID);
    }

/*    private Mono<Void> getItemFromModel(ItemModel itemModel, int imageID) throws InvalidItemTypeException {
        new ItemFactoryImp().makeItem(itemModel).changeImage(imageID);
    }*/

    private Mono<Integer> getUploadedImageID(ImagesEntity imageEntity) {
        return Mono.just(imageEntity.getImageID());
    }
}
