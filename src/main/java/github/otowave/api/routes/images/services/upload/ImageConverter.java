package github.otowave.api.routes.images.services.upload;

import com.luciad.imageio.webp.WebPImageWriterSpi;
import com.luciad.imageio.webp.WebPWriteParam;
import github.otowave.api.configuration.StaticContentDirs;
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
public class ImageConverter {
    private final String IMAGES_DIR = StaticContentDirs.IMAGES_DIR.getDir();

    public ImageConverter() {
    }

    protected Mono<Void> convertImageFileToWebp(int imageID, Mono<FilePart> imageFile) {
        return imageFile
                .flatMap(file -> {
                    Path inputFilePath = Paths.get(IMAGES_DIR, imageID + getFileExtension(file.filename()));
                    Path outputFilePath = Paths.get(IMAGES_DIR, imageID + ".webp");

                    return file.transferTo(inputFilePath)
                            .then(Mono.fromCallable(() -> {
                                convertToWebp(inputFilePath, outputFilePath);
                                deleteUnconvertedFile(inputFilePath);
                                return Mono.empty();
                            }))
                            .then();
                });
    }

    private void convertToWebp(Path inputFilePath, Path outputFilePath) throws IOException {
        File inputFile = inputFilePath.toFile();
        File outputFile = outputFilePath.toFile();
        Locale locale = new Locale("en", "US");

        try (ImageOutputStream output = ImageIO.createImageOutputStream(outputFile)) {
            BufferedImage image = ImageIO.read(inputFile);

            ImageWriterSpi writerSpi = new WebPImageWriterSpi();
            ImageWriter writer = writerSpi.createWriterInstance();

            writer.setOutput(output);
            WebPWriteParam webpWriteParam = new WebPWriteParam(locale);
            writer.write(null, new IIOImage(image, null, null), webpWriteParam);

            writer.dispose();
        }
    }

    private void deleteUnconvertedFile(Path path) throws IOException {
        Files.deleteIfExists(path);
    }

    public Mono<Void> deleteImageFile(int imageId) {
        return Mono.fromRunnable(() -> {
            File dir = new File(IMAGES_DIR);
            File[] files = dir.listFiles();

            if (files != null) {
                for (File file : files) {
                    int indexExtension = file.getName().lastIndexOf('.');
                    if (indexExtension >= 0 && file.getName().substring(0, indexExtension).equals(String.valueOf(imageId))) {
                        try {
                            Files.deleteIfExists(file.toPath());
                        } catch (IOException e) {
                            throw new RuntimeException("Error deleting file: " + file.getName(), e);
                        }
                        break;
                    }
                }
            }
        }).then();
    }

    private String getFileExtension(String filename) {
        int extensionIndex = filename.lastIndexOf('.');
        return filename.substring(extensionIndex);
    }
}
