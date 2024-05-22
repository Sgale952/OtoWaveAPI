package github.otowave.api.routes.images.services;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.images.entities.ImagesEntity;
import github.otowave.api.routes.images.repositories.ImagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.nio.file.Paths;

import static github.otowave.api.configuration.StaticContentDirs.IMAGES_DIR;

@Service
public class ImageSaver {
    @Autowired
    ImagesRepo imagesRepo;

    public ImageSaver() {
    }

    protected Mono<ImagesEntity> saveImage(ItemModel itemModel, Mono<FilePart> imageFile) {
        return imageFile.flatMap(file -> {
            Mono<ImagesEntity> newImageEntity = createImageEntity(itemModel, file).flatMap(this::saveImageEntity);
            Mono<Integer> newImageID = newImageEntity.flatMap(this::getUploadedImageID);
            newImageID.flatMap(imageID -> saveImageFile(imageFile, imageID));
            return newImageEntity;
        });
    }

    private Mono<ImagesEntity> createImageEntity(ItemModel itemModel, FilePart imageFile) {
        return Mono.fromCallable(() -> {
            int userID = itemModel.uploaderID();
            boolean animated = isImageAnimated(imageFile.filename());
            return new ImagesEntity(userID, animated);
        });
    }

    private Mono<ImagesEntity> saveImageEntity(ImagesEntity imageEntity) {
        return imagesRepo.save(imageEntity);
    }

    private Mono<Integer> getUploadedImageID(ImagesEntity imageEntity) {
        return Mono.just(imageEntity.getImageID());
    }

    private Mono<Void> saveImageFile(Mono<FilePart> imageFile, int imageID) {
        return imagePathBuilder(imageFile, imageID)
                .flatMap(path -> imageFile.flatMap(file -> file.transferTo(path)));
    }

    private Mono<Path> imagePathBuilder(Mono<FilePart> imageFile, int imageID) {
        return imageFile.map(file -> Paths.get(IMAGES_DIR.getDir() + imageID + getFileExtension(file.filename())));
    }

    private String getFileExtension(String filename) {
        int extensionIndex = filename.lastIndexOf('.');
        return filename.substring(extensionIndex);
    }

    private boolean isImageAnimated(String filename) {
        return filename.endsWith(".gif");
    }
}