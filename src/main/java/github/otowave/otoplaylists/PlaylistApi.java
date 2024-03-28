package github.otowave.otoplaylists;

import java.sql.*;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static github.otowave.api.UploadHelper.convertToInt;
import static github.otowave.api.DatabaseManager.getConnection;

public class PlaylistApi {
    private static final Logger logger = LoggerFactory.getLogger(PlaylistApi.class);
    private static final Gson gson = new Gson();


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

    public static String update(Request req, Response res) {
        return "";
    }

    public static String delete(Request req, Response res) {
        return "";
    }

    public static String like(Request req, Response res) {
        return "";
    }
}

record PlayListData(String title, int official, int access) {}
record MusicId(int musicId) {}