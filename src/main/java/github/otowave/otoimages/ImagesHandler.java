package github.otowave.otoimages;

import com.luciad.imageio.webp.WebPImageWriterSpi;
import com.luciad.imageio.webp.WebPWriteParam;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import spark.Request;
import spark.utils.IOUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;

import static github.otowave.api.UploadHelper.*;

public class ImagesHandler {
    private static final String IMAGES_DIR = "/home/otowave/data/images/";

    static void apply(ImagesApi.ImageData imageData, String imageId, Connection conn) throws SQLException, IOException {
        int prevImageId = convertToInt(imageData.prevImageId());
        String sql = imageApplier.applySelector(imageData.imageType());
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, imageId);
        stmt.setString(2, imageData.sourceId());
        stmt.executeUpdate();

        if(prevImageId > 4) {
            deleteImageFile(prevImageId);
        }
    }

    static void saveImageFile(Request req, String imageId) throws ServletException, IOException {
        Part imagePart = getStaticFilePart(req, "image");
        String fileExtension = getFileExtension(imagePart);
        String fileName = imageId+getFileExtension(imagePart);

        try(OutputStream outputStream = new FileOutputStream(IMAGES_DIR + fileName)) {
            IOUtils.copy(imagePart.getInputStream(), outputStream);
        }

        if(!fileExtension.equals(".gif") && !fileExtension.equals(".webp")) {
            convertToWebp(imageId, fileExtension);
        }
    }

    public static void convertToWebp(String imageId, String fileExtension) throws IOException {
        String input = IMAGES_DIR+imageId+fileExtension;

        File inputFile = new File(input);
        File outputFile = new File(IMAGES_DIR+imageId+".webp");
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

    static void deleteImageFile(int imageId) throws IOException {
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
}

class imageApplier {
    static String applySelector(String imageType) throws SQLException {
        return switch (imageType) {
            case "userAvatar" -> applyToUserAvatar();
            case "userHeader" -> applyToUserHeader();
            case "musicCover" -> applyToMusic();
            case "playlistCover" -> applyToPlaylist();
            default -> throw new SQLException("Incorrect image type");
        };
    }

    private static String applyToUserAvatar() {
        return "UPDATE users SET avatar_id = ? WHERE user_id = ?";
    }
    private static String applyToUserHeader() {
        return "UPDATE users SET header_id = ? WHERE user_id = ?";
    }
    private static String applyToMusic() {
        return "UPDATE music SET cover_id = ? WHERE music_id = ?";
    }
    private static String applyToPlaylist() {
        return "UPDATE playlists SET cover_id = ? WHERE playlist_id = ?";
    }
}
