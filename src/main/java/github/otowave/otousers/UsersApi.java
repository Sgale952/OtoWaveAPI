package github.otowave.otousers;

import com.google.gson.Gson;
import github.otowave.otomusic.MusicApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.sql.*;

import static github.otowave.api.DatabaseManager.getConnection;

public class UsersApi {
    private static final Logger logger = LoggerFactory.getLogger(MusicApi.class);
    private static final Gson gson = new Gson();

    //Add sha256 encrypt
    public static String upload(Request req, Response res) {
        UserData userData = gson.fromJson(req.body(), UserData.class);
        int userId = 0;

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO users (access, nickname, email, password) " +
                    "VALUES (?, '?', '?', '?')";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, userData.access());
            stmt.setString(2, userData.nickname());
            stmt.setString(3, userData.email());
            stmt.setString(4, userData.password());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            userId = generatedKeys.getInt(1);

            res.status(201);
        }
        catch (SQLException e) {
            logger.error("Error in UserApi.upload", e);
            res.status(500);
        }

        return String.valueOf(userId);
    }
}

record UserData(int access, String nickname, String email, String password) {}
