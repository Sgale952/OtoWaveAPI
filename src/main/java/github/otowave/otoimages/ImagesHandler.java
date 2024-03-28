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
    //private static final String IMAGES_DIR = "/home/otowave/data/images/";
    private static final String IMAGES_DIR = "D:\\i\\";

    protected static String applyImage(String imageType) {
        return switch (imageType) {
            case "musicCover" -> applyToMusic();
            case "playlistCover" -> applyToPlaylist();
            case "userAvatar" -> applyToUserAvatar();
            case "userHeader" -> applyToUserHeader();
            default -> "";
        };
    }

    private static String applyToMusic() {
        return "UPDATE music SET cover_id = ? WHERE music_id = ?";
    }

    private static String applyToPlaylist() {
        return "UPDATE playlists SET cover_id = ? WHERE playlist_id = ?";
    }

    private static String applyToUserAvatar() {
        return "UPDATE users SET avatar_id = ? WHERE user_id = ?";
    }

    private static String applyToUserHeader() {
        return "UPDATE users SET header_id = ? WHERE user_id = ?";
    }

    protected static void saveImageFile(Request req, String imageId) throws ServletException, IOException {
        Part imagePart = getStaticFilePart(req, "image");
        String fileName = imageId+getFileExtension(imagePart);

        try(OutputStream outputStream = new FileOutputStream(IMAGES_DIR + fileName)) {
            IOUtils.copy(imagePart.getInputStream(), outputStream);
        }
    }

    protected static void deleteImageFile(int imageId) throws IOException {
        File dir = new File(IMAGES_DIR);
        File[] files = dir.listFiles();

        for(File file : files) {
            int indexExtension = file.getName().lastIndexOf('.');

            if(file.getName().substring(0, indexExtension).equals(String.valueOf(imageId))) {
                Path filePath = Path.of(file.getAbsolutePath());
                Files.delete(filePath);
                break;
            }
        }
    }
}
