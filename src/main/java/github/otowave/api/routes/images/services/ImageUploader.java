package github.otowave.api.routes.images.services;

import github.otowave.api.routes.common.models.ItemModel;
import github.otowave.api.routes.images.entities.ImagesEntity;
import github.otowave.api.routes.images.repositories.ImagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ImageUploader {
    private final String IMAGES_DIR = "D:\\Archive\\";
    @Autowired
    ImagesRepo imagesRepo;

    public ImageUploader() {
    }

    Mono<ImagesEntity> uploadImage(ItemModel itemModel, Mono<FilePart> imageFile) {
        return imageFile
                .flatMap(file -> createImageEntity(itemModel, file))
                .flatMap(this::saveImageEntity);
    }

    private Mono<ImagesEntity> createImageEntity(ItemModel itemModel, FilePart imageFile) {
        return Mono.fromCallable(() -> {
            boolean animated = isAnimated(imageFile.filename());
            return new ImagesEntity(itemModel.uploaderID(), animated);
        });
    }

    private Mono<ImagesEntity> saveImageEntity(ImagesEntity imageEntity) {
        return imagesRepo.save(imageEntity);
    }

    private boolean isAnimated(String filename) {
        return filename.endsWith(".gif");
    }

/*    private void saveImageFile() {
        imageFile.flatMap(file -> {
            file.transferTo(Paths.get(IMAGES_DIR + file.filename()));
            fileName = file.filename();
            return imageFile;
        }).doOnError(error -> {
            throw new FileSaveException("Image file not saved", error);
        });
    }

    public void convertImageFileToWebp() throws IOException {
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
}