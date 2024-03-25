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

import static github.otowave.api.UploadHelper.getFileExtension;
import static github.otowave.api.UploadHelper.getStaticFilePart;

public class ImagesHandler {
    private static final String IMAGES_DIR = "/home/otowave/data/images/";

    protected static String applyImage(String imageType, String imageId, String sourceId) {
        return switch (imageType) {
            case "musicCover" -> applyToMusic(imageId, sourceId);
            case "playlistCover" -> applyToPlaylist(imageId, sourceId);
            case "userAvatar" -> applyToUserAvatar(imageId, sourceId);
            case "userHeader" -> applyToUserHeader(imageId, sourceId);
            default -> "";
        };
    }

    private static String applyToMusic( String imageId, String sourceId) {
        return "UPDATE music SET cover_id = "+imageId+" WHERE music_id = "+sourceId;
    }

    private static String applyToPlaylist(String imageId, String sourceId) {
        return "UPDATE playlists SET cover_id = "+imageId+" WHERE playlist_id = "+sourceId;
    }

    private static String applyToUserAvatar(String imageId, String sourceId) {
        return "UPDATE users SET avatar_id = "+imageId+" WHERE user_id = "+sourceId;
    }

    private static String applyToUserHeader(String imageId, String sourceId) {
        return "UPDATE users SET header_id = "+imageId+" WHERE user_id = "+sourceId;
    }

    protected static void saveImageFile(Request req, String imageId) throws ServletException, IOException {
        Part imagePart = getStaticFilePart(req, "image");
        String fileName = imageId+getFileExtension(imagePart);

        try(OutputStream outputStream = new FileOutputStream(IMAGES_DIR + fileName)) {
            IOUtils.copy(imagePart.getInputStream(), outputStream);
        }
    }

    protected static void deleteImageFile(String imageId) throws IOException {
        File dir = new File(IMAGES_DIR);
        File[] files = dir.listFiles();

        for(File file : files) {
            int indexExtension = file.getName().lastIndexOf('.');

            if(file.getName().substring(0, indexExtension).equals(imageId)) {
                Path filePath = Path.of(file.getAbsolutePath());
                Files.delete(filePath);
                break;
            }
        }
    }
}
