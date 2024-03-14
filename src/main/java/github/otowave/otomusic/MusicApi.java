package github.otowave.otomusic;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static github.otowave.api.DatabaseManager.getConnection;

public class MusicApi extends MusicHandler {
    private static final Logger logger = LoggerFactory.getLogger(MusicApi.class);
    private static final Gson gson = new Gson();

    public static String allData(Request request, Response response) {
        return "";
    }

    public static String add(Request request, Response response) {
        return "";
    }

    public static String update(Request request, Response response) {
        return "";
    }

    public static String delete(Request request, Response response) {
        return "";
    }

    public static String dailyRandom(Request request, Response response) {
        return "";
    }

    public static String search(Request request, Response response) {
        return "";
    }

    //TODO: need tests
    public static String topPerMonth(Request request, Response response) {
        Map<Integer, String> musicIds = new LinkedHashMap<>();
        String sortBy = request.contextPath().equals("/recent") ? "uploaded" : "listens";

        try(Connection conn = getConnection()) {
            String sql = "SELECT music_id, genre FROM music WHERE MONTH(uploaded) = MONTH(CURRENT_DATE()) AND YEAR(uploaded) = YEAR(CURRENT_DATE()) ORDER BY " +
                         sortBy + " DESC LIMIT 100";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    musicIds.put(rs.getInt("music_id"), rs.getString("genre"));
                }

            response.status(200);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.topPerMonth", e);
            response.status(500);
        }

        return gson.toJson(musicIds);
    }

    //TODO: need tests
    public static String like(Request request, Response response) {
        int userId = convertParamsToInt(response, request.params(":userId"));
        int musicId = convertParamsToInt(response, request.queryParams("musicId"));
        UserMusicRelation data = new UserMusicRelation(userId, musicId);

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO likedSongs (user_id, music_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.userId());
            stmt.setInt(2, data.musicId());
            stmt.executeUpdate();

            response.status(201);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.like", e);
            response.status(500);
        }

        return "";
    }

    //TODO: need tests
    public static String discard(Request request, Response response) {
        int userId = convertParamsToInt(response, request.params(":userId"));
        int musicId = convertParamsToInt(response, request.queryParams("musicId"));
        UserMusicRelation data = new UserMusicRelation(userId, musicId);

        try(Connection conn = getConnection()) {
            String sql = "DELETE FROM likedSongs WHERE user_id = ? AND song_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.userId());
            stmt.setInt(2, data.musicId());
            stmt.executeUpdate();

            response.status(201);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.discard", e);
            response.status(500);
        }

        return "";
    }

    //TODO: need tests
    public static String updateLikes(Request request, Response response) {
        int musicId = convertParamsToInt(response, request.params(":musicId"));
        UserMusicRelation data = new UserMusicRelation(-1, musicId);


        try(Connection conn = getConnection()) {
            String sql = "UPDATE music SET likes = likes + 1 WHERE music_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.musicId());
            stmt.executeUpdate();

            response.status(201);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.updateLikes", e);
            response.status(500);
        }

        return "";
    }

    //TODO: need tests
    public static String updateListens(Request request, Response response) {
        int musicId = convertParamsToInt(response, request.params(":musicId"));
        UserMusicRelation data = new UserMusicRelation(-1, musicId);


        try(Connection conn = getConnection()) {
            String sql = "UPDATE music SET listens = listens + 1 WHERE music_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.musicId());
            stmt.executeUpdate();

            response.status(201);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.updateLikes", e);
            response.status(500);
        }

        return "";
    }
}

record UserMusicRelation(int userId, int musicId) {
}