package github.otowave.api.routes.images.services.upload;

import github.otowave.api.routes.images.entities.ImagesEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@Service
public class UploadHelper {
    public UploadHelper() {
    }

    public Mono<Integer> getImageID(ImagesEntity imageEntity) {
        return Mono.just(imageEntity.getImageID());
    }

    public Mono<Void> deleteImageFile(File file) {
        return Mono.fromRunnable(() -> {
            try {
                Files.deleteIfExists(file.toPath());
            }
            catch (IOException e) {
                //TODO: need handle exception
            }
        });
    }

    public boolean isImageAnimated(String filename) {
        return filename.endsWith(".gif");
    }
}
