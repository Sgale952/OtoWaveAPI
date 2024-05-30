package github.otowave.api.routes.images.services.upload.save;

import github.otowave.api.routes.images.entities.ImagesEntity;
import github.otowave.api.routes.images.repositories.ImagesRepo;
import github.otowave.api.routes.images.services.upload.UploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static github.otowave.api.configuration.StaticContentDirs.IMAGES_DIR;

@Service
public class ImageSaver {
    @Autowired
    ImagesRepo imagesRepo;
    @Autowired
    UploadHelper uploadHelper;
    @Autowired
    ImageConverter imageConverter;

    public ImageSaver() {
    }

    @Transactional
    public Mono<ImagesEntity> saveImage(Mono<FilePart> imageFile) {
        return imageFile
                .flatMap(this::createImageEntity)
                .flatMap(this::saveImageEntity)
                .flatMap(imageEntity -> uploadHelper.getImageID(imageEntity)
                        .flatMap(imageID -> saveImageFile(imageFile, imageID)
                                .flatMap(path -> imageConverter.convertImageToWebp(path))
                        )
                        .thenReturn(imageEntity)
                );
    }

    private Mono<ImagesEntity> createImageEntity(FilePart imageFile) {
        return Mono.fromCallable(() -> {
            boolean animated = uploadHelper.isImageAnimated(imageFile.filename());
            return new ImagesEntity(animated);
        });
    }

    private Mono<ImagesEntity> saveImageEntity(ImagesEntity imageEntity) {
        return imagesRepo.save(imageEntity);
    }

    private Mono<File> saveImageFile(Mono<FilePart> imageFile, int imageID) {
        return imageFile.flatMap(file -> imagePathBuilder(file, imageID)
                        .flatMap(path -> file.transferTo(path).thenReturn(new File(path.toString()))));
    }

    private Mono<Path> imagePathBuilder(FilePart imageFile, int imageID) {
        return Mono.fromCallable(() -> Paths.get(IMAGES_DIR.getDir() + imageID + getFileExtension(imageFile.filename())));
    }

    private String getFileExtension(String filename) {
        int extensionIndex = filename.lastIndexOf('.');
        return filename.substring(extensionIndex);
    }
}