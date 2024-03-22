package github.otowave.otoimages;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import spark.Request;
import spark.utils.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImagesHandler {

    //This is shit
    protected static String applyImage(String imageType, int imageId, int sourceId) {
        return switch (imageType) {
            case "musicCover" -> applyToMusic(imageId, sourceId);
            case "playlistCover" -> applyToPlaylist(imageId, sourceId);
            case "userAvatar" -> applyToUserAvatar(imageId, sourceId);
            case "userHeader" -> applyToUserHeader(imageId, sourceId);
            default -> "";
        };

    }

    private static String applyToMusic( int imageId, int sourceId) {
        return "UPDATE music SET cover_id = "+imageId+" WHERE music_id = "+sourceId;
    }

    private static String applyToPlaylist(int imageId, int sourceId) {
        return "UPDATE playlists SET cover_id = "+imageId+" WHERE playlist_id = "+sourceId;
    }

    private static String applyToUserAvatar(int imageId, int sourceId) {
        return "UPDATE users SET avatar_id = "+imageId+" WHERE user_id = "+sourceId;
    }

    private static String applyToUserHeader(int imageId, int sourceId) {
        return "UPDATE users SET header_id = "+imageId+" WHERE user_id = "+sourceId;
    }

    //TODO: Change save dir in Linux
    protected static void saveImageFile(Request req, int imageId) {
        Part imagePart = getImagePart(req);
        String fileName = imageId+getImageExtension(imagePart);

        try (OutputStream outputStream = new FileOutputStream("D:\\" + fileName)) {
            IOUtils.copy(imagePart.getInputStream(), outputStream);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Part getImagePart(Request req) {
        Part imagePart;

        try {
            imagePart = req.raw().getPart("image");
        }
        catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }

        return imagePart;
    }

    private static String getImageExtension(Part imagePart) {
        String fileName = imagePart.getSubmittedFileName();
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    protected static void deleteImageFile() {
    }
}
