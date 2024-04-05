package github.otowave.otoplaylists;

import java.sql.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import github.otowave.otoimages.ImagesApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static github.otowave.api.UploadHelper.convertToInt;
import static github.otowave.api.DatabaseManager.getConnection;
import static github.otowave.api.UploadHelper.haveAttribute;
import static github.otowave.otoplaylists.PlaylistHandler.*;

public class PlaylistApi {
    private static final Logger logger = LoggerFactory.getLogger(PlaylistApi.class);
    private static final Gson gson = new Gson();

    //TODO: need tests
    public static String allData(Request req, Response res) {
        JsonObject jsonOutput = new JsonObject();
        int playlistId = convertToInt(req.params(":playlistId"));

        try(Connection conn = getConnection()) {
            String sql = "SELECT * FROM playlists WHERE playlist_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, playlistId);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                jsonOutput.addProperty("playlistId", rs.getInt("playlist_id"));
                jsonOutput.addProperty("creatorId", rs.getInt("creator_id"));
                jsonOutput.addProperty("coverId", rs.getInt("cover_id"));
                jsonOutput.addProperty("title", rs.getString("title"));
                jsonOutput.addProperty("official", rs.getString("official"));
                jsonOutput.addProperty("access", rs.getInt("access"));
                jsonOutput.addProperty("likes", rs.getInt("likes"));
                jsonOutput.addProperty("listens", rs.getInt("listens"));
            }

            JsonArray jsonMusicIds = getMusicFilling(playlistId, conn);
            jsonOutput.add("musicIds", jsonMusicIds);

            res.status(200);
        }
        catch(SQLException e) {
            logger.error("Error in PlaylistApi.allData", e);
            res.status(500);
        }

        return gson.toJson(jsonOutput);
    }

    /* Worked / Unstable / Unsafe */
    public static String upload(Request req, Response res) {
        PlayListData playListData = gson.fromJson(req.body(), PlayListData.class);
        int creatorId = convertToInt(req.params(":userId"));
        String palylistId = "";

        try(Connection conn = getConnection()) {
            String sql =  "INSERT INTO playlists (creator_id, title, official, access)" +
                          "VALUES(?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, creatorId);
            stmt.setString(2, playListData.title());
            stmt.setInt(3, playListData.official());
            stmt.setInt(4, playListData.access());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next()) {
                palylistId = generatedKeys.getString(1);
            }

            res.status(201);
        }
        catch (SQLException e) {
            logger.error("Error in PlaylistApi.upload", e);
            res.status(500);
        }

        return palylistId;
    }

    public static String update(Request req, Response res) {
        return "";
    }

    /* Worked / Unstable / Unsafe */
    public static String delete(Request req, Response res) {
        int playlistId = haveAttribute("playlistId", req)? req.attribute("playlistId") : convertToInt(req.params(":playlistId"));
        int softDelete = convertToInt(req.queryParams("softDelete"));

        try(Connection conn = getConnection()) {
            JsonArray musicIds = getMusicFilling(playlistId, conn);
            int imageId = getCoverId(playlistId, conn);
            String sql = "DELETE FROM playlists WHERE playlist_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, playlistId);

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0) {
                ImagesApi.delete(imageId);
                if(softDelete == 0) {
                    deleteAllMusic(musicIds, req, res);
                }

                res.status(200);
            }
            else {
                res.status(404);
            }
        }
        catch (SQLException e) {
            logger.error("Error in PlaylistApi.delete", e);
            res.status(500);
        }

        return "";
    }

    /* Worked / Unstable / Unsafe */
    public static String addMusic(Request req, Response res) {
        int playlistId = convertToInt(req.params(":playlistId"));
        MusicId musicId = gson.fromJson(req.body(), MusicId.class);

        try(Connection conn = getConnection()) {
            String sql =  "INSERT INTO fillingPlaylists (playlist_id, music_id)" +
                    "VALUES(?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, playlistId);
            stmt.setInt(2, musicId.musicId());
            stmt.executeUpdate();

            res.status(201);
        }
        catch (SQLException e) {
            logger.error("Error in PlaylistApi.addMusic", e);
            res.status(500);
        }

        return "";
    }

    public static String deleteMusic(Request req, Response res) {
        return "";
    }

    public static String updateLikes(Request req, Response res) {
        return "";
    }

    record PlayListData(String title, int official, int access) {}
    record MusicId(int musicId) {}
}