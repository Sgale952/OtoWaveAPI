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
        int palylistId = 0;

        try(Connection conn = getConnection()) {
            String sql =  "INSERT INTO playlists (official, access, creator, title)" +
                          "VALUES(?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, playListData.official());
            stmt.setInt(2, playListData.access());
            stmt.setInt(3, creatorId);
            stmt.setString(4, playListData.title());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next()) {
                palylistId = generatedKeys.getInt(1);
            }

            res.status(201);
        }
        catch (SQLException e) {
            logger.error("Error in PlaylistApi.upload", e);
            res.status(500);
        }

        return String.valueOf(palylistId);
    }

    public static String addSong(Request req, Response res) {
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

record PlayListData(int official, int access, String title) {}