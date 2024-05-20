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

    Mono<ImagesEntity> saveImage(ItemModel itemModel, Mono<FilePart> imageFile) {
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

    public Mono<Void> saveImageFile(Mono<FilePart> imageFile, int imageID) {
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
/*    public void convertImageFileToWebp() throws IOException {
        final String input = IMAGES_DIR;
        imageFile.flatMap(file -> input += file.filename());

        File inputFile = new File(input);
        File outputFile = new File(IMAGES_DIR+imageID+".webp");
        Locale locale = new Locale("en", "US");

        try(ImageOutputStream output = ImageIO.createImageOutputStream(outputFile)) {
            BufferedImage image = ImageIO.read(inputFile);

            ImageWriterSpi writerSpi = new WebPImageWriterSpi();
            ImageWriter writer = writerSpi.createWriterInstance();

            writer.setOutput(output);
            WebPWriteParam webpWriteParam = new WebPWriteParam(locale);
            writer.write(null, new IIOImage(image, null, null), webpWriteParam);

            writer.dispose();
        }

        deleteUnconvertedFile(input);
    }

    private void deleteImageFile(int imageId) throws IOException {
        File dir = new File(IMAGES_DIR);
        File[] files = dir.listFiles();

        assert files != null;
        for(File file : files) {
            int indexExtension = file.getName().lastIndexOf('.');
            if(indexExtension >= 0 && file.getName().substring(0, indexExtension).equals(String.valueOf(imageId))) {
                Path filePath = Path.of(file.getAbsolutePath());
                Files.delete(filePath);
                break;
            }
        }
    }

    public static void deleteUnconvertedFile(String path) {
        File file = new File(path);
        file.delete();
    }*/