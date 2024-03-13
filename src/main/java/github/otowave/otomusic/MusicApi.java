package github.otowave.otomusic;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static github.otowave.api.DatabaseManager.getConnection;

public class MusicApi extends MusicHandler {
    private static final Logger logger = LoggerFactory.getLogger(MusicApi.class);
    private static final Gson gson = new Gson();

    public static String allData(Request request) {
        return "";
    }

    public static String add(Request request) {
        return "";
    }

    public static String update(Request request) {
        return "";
    }

    public static String delete(Request request) {
        return "";
    }

    public static String dailyRandom(Request request) {
        return "";
    }

    public static String search(Request request) {
        return "";
    }

    public static String sortByGenre(Request request) {
        return "";
    }

    public static String recentUploaded(Request request) {
        return "";
    }

    public static String mostListensPerMonth(Request request) {
        return "";
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