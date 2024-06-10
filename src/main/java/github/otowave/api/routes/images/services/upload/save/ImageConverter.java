package github.otowave.api.routes.images.services.upload.save;

import com.luciad.imageio.webp.WebPImageWriterSpi;
import com.luciad.imageio.webp.WebPWriteParam;
import github.otowave.api.exceptions.FileNotNeedConversionException;
import github.otowave.api.routes.images.services.upload.UploadHelper;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.nio.file.Paths;

import static github.otowave.api.configuration.StaticContentDirs.IMAGES_DIR;

@Service
public class ImageConverter {
    @Autowired
    UploadHelper uploadHelper;

    protected Mono<Void> convertImageToWebp(File inputFile) {
        return Mono.fromCallable(() -> {
            isNeedConversion(inputFile);
            return Paths.get(IMAGES_DIR.getDir(), getFileNameWithoutExtension(inputFile) + ".webp").toFile();

        }).flatMap(outputFile -> rewriteImageToWebp(inputFile, outputFile))
          .onErrorResume(FileNotNeedConversionException.class, e -> Mono.empty()).then();
    }

    private void isNeedConversion(File inputFile) {
        String fileName = inputFile.getName();
        if (isAppropriateExtension(fileName))
            throw new FileNotNeedConversionException(fileName);
    }

    private Mono<Void> rewriteImageToWebp(File inputFile, File outputFile) {
        return Mono.fromCallable(() -> {
            try (ImageOutputStream output = ImageIO.createImageOutputStream(outputFile)) {
                BufferedImage image = ImageIO.read(inputFile);
                writeToWebp(output, image);
            }
            catch (IOException e) {
                //TODO: need handle exception
            }
            return inputFile;
        }).flatMap(uploadHelper::deleteImageFile); //delete unconverted image file
    }

    private void writeToWebp(ImageOutputStream output, BufferedImage image) throws IOException {
        ImageWriter writer = getWriter();

        writer.setOutput(output);
        WebPWriteParam webpWriteParam = new WebPWriteParam(null);
        writer.write(null, new IIOImage(image, null, null), webpWriteParam);

        writer.dispose();
    }

    private ImageWriter getWriter() throws IOException {
        ImageWriterSpi writerSpi = new WebPImageWriterSpi();
        return writerSpi.createWriterInstance();
    }

    private boolean isAppropriateExtension(String filename) {
        return uploadHelper.isImageAnimated(filename) || filename.endsWith(".webp");
    }

    private String getFileNameWithoutExtension(File file) {
        String fullName = file.getName();
        int extensionIndex = fullName.lastIndexOf('.');
        return fullName.substring(0, extensionIndex);
    }
}