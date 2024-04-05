package github.otowave.otoimages;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import spark.Request;
import spark.utils.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static github.otowave.api.UploadHelper.*;

public class ImagesHandler {
    //private static final String IMAGES_DIR = "/home/otowave/data/images/";
    private static final String IMAGES_DIR = "D:\\s\\";

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
        String fileName = imageId+getFileExtension(imagePart);

        try(OutputStream outputStream = new FileOutputStream(IMAGES_DIR + fileName)) {
            IOUtils.copy(imagePart.getInputStream(), outputStream);
        }
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

    private static String applyToUserAvatar() {return "UPDATE users SET avatar_id = ? WHERE user_id = ?";}
    private static String applyToUserHeader() {return "UPDATE users SET header_id = ? WHERE user_id = ?";}
    private static String applyToMusic() {return "UPDATE music SET cover_id = ? WHERE music_id = ?";}
    private static String applyToPlaylist() {return "UPDATE playlists SET cover_id = ? WHERE playlist_id = ?";}
}
