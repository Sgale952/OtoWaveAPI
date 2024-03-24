package github.otowave.otoplaylists;
import java.sql.*;

import com.google.gson.Gson;
import github.otowave.otomusic.MusicApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static github.otowave.api.DatabaseManager.getConnection;

public class PlaylistApi {
    private static final Logger logger = LoggerFactory.getLogger(MusicApi.class);
    private static final Gson gson = new Gson();


    public static String add(Request req, Response res) {
        int palylistID = 0;
        try(Connection conn = getConnection())
        {
            String sql =  "INSERT INTO playlists (title,creator, official, public)"+
                    "VALUES(?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,PlayListData.title());
            stmt.setInt(2, PlayListData.creator());
            stmt.setInt(3,PlayListData.official());
            stmt.setInt(3,PlayListData.piblic());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            palylistID = generatedKeys.getInt(1);
            res.status(201);
        }
        catch (SQLException e)
        {
            logger.error("Error in PlaylistApi.add", e);
            res.status(500);
        }
        return "";
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
record PlayListData(int official, String title, int creator,int piblic) {}
