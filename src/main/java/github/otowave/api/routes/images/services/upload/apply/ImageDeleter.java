package github.otowave.api.routes.images.services.upload.apply;

import github.otowave.api.exceptions.ImageNotNeedDeletion;
import github.otowave.api.routes.common.services.items.factory.Item;
import github.otowave.api.routes.images.repositories.ImagesRepo;
import github.otowave.api.routes.images.services.upload.UploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;

import static github.otowave.api.configuration.StaticContentDirs.IMAGES_DIR;

@Service
public class ImageDeleter {
    @Autowired
    ImagesRepo imagesRepo;
    @Autowired
    UploadHelper UploadHelper;
    public ImageDeleter() {
    }

    protected Mono<Void> deletePastImage(Mono<Item> item) {
        return item.flatMap(i -> i
                .getCurrentImageID()
                .flatMap(imageID -> {
                    isNeedDeletion(imageID);
                    return deleteFileAndDatabaseRecord(imageID);

                }).onErrorResume(ImageNotNeedDeletion.class, e -> Mono.empty()).then()
        );
    }

    private void isNeedDeletion(int imageID) {
        if (isDefaultImageID(imageID))
            throw new ImageNotNeedDeletion("imageID: "+imageID);
    }

    private boolean isDefaultImageID(int imageID) {
        return imageID <= 5;
    }

    private Mono<Void> deleteFileAndDatabaseRecord(int imageID) {
        return imagesRepo.deleteById(imageID)
                .then(UploadHelper.deleteImageFile(getImageFile(imageID, ".webp")))
                .then(UploadHelper.deleteImageFile(getImageFile(imageID, ".gif")));
    }

    private File getImageFile(int imageID, String extension) {
        return new File(IMAGES_DIR.getDir() + imageID +  extension);
    }
}