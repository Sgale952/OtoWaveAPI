package github.otowave.otousers;

import github.otowave.otoimages.ImagesApi;
import github.otowave.otomusic.MusicApi;
import github.otowave.otoplaylists.PlaylistApi;
import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersHandler {
    static void deleteAllData(ArrayList<Integer> musicIds, ArrayList<Integer> imagesIds, ArrayList<Integer> playlistsIds, Request req, Response res) {
        deleteAllPlaylists(playlistsIds, req, res);
        deleteAllMusic(musicIds, req, res);
        deleteAllImages(imagesIds, req, res);
    }
    private static void deleteAllPlaylists(ArrayList<Integer> playlistsIds, Request req, Response res) {
        for(int id : playlistsIds) {
            req.attribute("playlistId", id);
            PlaylistApi.delete(req, res);
        }
    }
    private static void deleteAllMusic(ArrayList<Integer> musicIds, Request req, Response res) {
        for(int id : musicIds) {
            req.attribute("musicId", id);
            MusicApi.delete(req, res);
        }
    }
    private static void deleteAllImages(ArrayList<Integer> imagesIds, Request req, Response res) {
        for(int id : imagesIds) {
            ImagesApi.delete(id);
        }
    }
}

class Activities {
    static ArrayList<Integer> activitySelector(String type, int userId, Connection conn) throws SQLException {
        return switch (type) {
            case "listenHistory" -> getListenHistory(userId, conn);
            case "likedMusic" -> getLikedMusic(userId, conn);
            case "likedPlaylists" -> getLikedPlaylists(userId, conn);
            case "likedAuthors" -> getLikedAuthors(userId, conn);
            case "createdMusic" -> getCreatedMusic(userId, conn);
            case "createdImages" -> getCreatedImages(userId, conn);
            case "createdPlaylists" -> getCreatedPlaylists(userId, conn);
            default -> throw new SQLException("Incorrect activity type");
        };
    }

    private static ArrayList<Integer> getListenHistory(int userId, Connection conn)  throws SQLException {
        ArrayList<Integer> musicIds = new ArrayList<>();
        String sql = "SELECT * FROM listenedMusic WHERE user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            musicIds.add(rs.getInt("music_id"));
        }

        return musicIds;
    }

    private static ArrayList<Integer> getLikedMusic(int userId, Connection conn) throws SQLException {
        ArrayList<Integer> musicIds = new ArrayList<>();
        String sql = "SELECT * FROM likedMusic WHERE user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            musicIds.add(rs.getInt("music_id"));
        }

        return musicIds;
    }
    private static ArrayList<Integer> getLikedPlaylists(int userId, Connection conn) throws SQLException {
        ArrayList<Integer> playlistsIds = new ArrayList<>();
        String sql = "SELECT * FROM likedPlaylists WHERE user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            playlistsIds.add(rs.getInt("playlist_id"));
        }

        return playlistsIds;
    }
    private static ArrayList<Integer> getLikedAuthors(int userId, Connection conn) throws SQLException {
        ArrayList<Integer> usersIds = new ArrayList<>();
        String sql = "SELECT * FROM likedAuthors WHERE user_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            usersIds.add(rs.getInt("author_id"));
        }

        return usersIds;
    }

    private static ArrayList<Integer> getCreatedMusic(int userId, Connection conn) throws SQLException {
        ArrayList<Integer> musicIds = new ArrayList<>();
        String sql = "SELECT * FROM music WHERE author_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            musicIds.add(rs.getInt("music_id"));
        }

        return musicIds;
    }
    private static ArrayList<Integer> getCreatedImages(int userId, Connection conn) throws SQLException {
        ArrayList<Integer> imagesIds = new ArrayList<>();
        String sql = "SELECT * FROM images WHERE uploader_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            imagesIds.add(rs.getInt("image_id"));
        }

        return imagesIds;
    }
    private static ArrayList<Integer> getCreatedPlaylists(int userId, Connection conn) throws SQLException {
        ArrayList<Integer> playlistsIds = new ArrayList<>();
        String sql = "SELECT * FROM playlists WHERE creator_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, userId);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            playlistsIds.add(rs.getInt("playlist_id"));
        }

        return playlistsIds;
    }
}