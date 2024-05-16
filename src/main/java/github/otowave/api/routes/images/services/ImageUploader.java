package github.otowave.api.routes.images.services;

import com.luciad.imageio.webp.WebPImageWriterSpi;
import com.luciad.imageio.webp.WebPWriteParam;
import github.otowave.api.exceptions.FileSaveException;
import github.otowave.api.exceptions.InvalidItemTypeException;
import github.otowave.api.routes.images.models.ItemModel;
import github.otowave.api.routes.images.entities.ImagesEntity;
import github.otowave.api.routes.images.repositories.ImagesRepo;
import github.otowave.api.routes.common.services.items.factory.ItemFactoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

@Service
public class ImageUploader {
    private final String IMAGES_DIR = "D:\\Archive\\";
    private ItemModel itemModel;
    private int userID;
    private int imageID;
    private boolean animated;
    private ImagesEntity imageEntity;
    private Mono<FilePart> imageFile;
    @Autowired
    ImagesRepo imagesRepo;

    public ImageUploader() {
    }

    public ImageUploader(ItemModel itemModel, ImagesEntity imageEntity, Mono<FilePart> imageFile) {
        this.itemModel = itemModel;
        this.userID = imageEntity.getUploaderID();
        this.animated = imageEntity.isAnimated();
        this.imageFile = imageFile;
        this.imageEntity = imageEntity;
    }

    private void applyImageToItem() throws InvalidItemTypeException {
        new ItemFactoryImp().makeItem(itemModel).changeImage(imageID);
    }

    private void setImageID() {
         this.imageID = imageEntity.getImageID();
    }

    private ImagesEntity saveImageEntity() {
        return imagesRepo.save(imageEntity);
    }

    private void saveImageFile() {
        imageFile.flatMap(file -> file.transferTo(Paths.get(IMAGES_DIR +file.filename()))).doOnError(error -> {
            throw new FileSaveException("Image file not saved", error);
        });
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
}