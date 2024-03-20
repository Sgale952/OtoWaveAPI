package github.otowave.otoimages;

import spark.Request;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImagesHandler {

    //This is shit
    protected static String applyImage(String imageType, int sourceId, int imageId) {
        return switch (imageType) {
            case "musicCover" -> applyToMusic(sourceId, imageId);
            case "playlistCover" -> applyToPlaylist(sourceId, imageId);
            case "userAvatar" -> applyToUserAvatar(sourceId, imageId);
            case "userHeader" -> applyToUserHeader(sourceId, imageId);
            default -> "";
        };

    }

    private static String applyToMusic(int sourceId, int imageId) {
        return "UPDATE music SET cover_id = "+imageId+" WHERE music_id = "+sourceId;
    }

    private static String applyToPlaylist(int sourceId, int imageId) {
        return "UPDATE playlists SET cover_id = "+imageId+" WHERE playlist_id = "+sourceId;
    }

    private static String applyToUserAvatar(int sourceId, int imageId) {
        return "UPDATE users SET avatar_id = "+imageId+" WHERE user_id = "+sourceId;
    }

    private static String applyToUserHeader(int sourceId, int imageId) {
        return "UPDATE users SET header_id = "+imageId+" WHERE user_id = "+sourceId;
    }

    protected static void saveImageFile(Request req, int imageId) throws IOException {
        InputStream inputStream = req.raw().getInputStream();
        String fileName = imageId + req.queryParams("fileType");
        String dir = "/home/otowave/data/images/";

        Path dirPath = Path.of(dir);
        Path filePath = dirPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    }

    protected static void deleteImageFile() {
    }
}
