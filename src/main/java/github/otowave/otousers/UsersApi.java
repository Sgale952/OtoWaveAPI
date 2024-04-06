package github.otowave.otousers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;

import static github.otowave.api.DatabaseManager.getConnection;
import static github.otowave.api.UploadHelper.convertToInt;
import static github.otowave.otousers.Activities.activitySelector;
import static github.otowave.otousers.UsersHandler.*;

public class UsersApi {
    private static final Logger logger = LoggerFactory.getLogger(UsersApi.class);
    private static final Gson gson = new Gson();

    /* Worked / Unstable / Unsafe */
    public static String upload(Request req, Response res) {
        UserData userData = gson.fromJson(req.body(), UserData.class);
        String userId = "";

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO users (nickname, email, passwrd, access) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, userData.nickname());
            stmt.setString(2, userData.email());
            stmt.setString(3, userData.password());
            stmt.setInt(4, userData.access());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next()) {
                userId = generatedKeys.getString(1);
            }

            res.status(201);
        }
        catch(SQLException e) {
            logger.error("Error in UserApi.upload", e);
            res.status(500);
        }

        return userId;
    }

    public static String recovery(Request req, Response res) {
        return "";
    }

    public static String login(Request req, Response res) {
        UserData userData = gson.fromJson(req.body(), UserData.class);

        try(Connection conn = getConnection()) {
            String sql = "SELECT passwrd FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, userData.email());

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                String hashedPasswordFromDB = rs.getString("passwrd");
                if(userData.password().equals(hashedPasswordFromDB)) {
                    res.status(200);
                }
                else {
                    res.status(401);
                }
            }
            else {
                res.status(404);
            }

        }
        catch(SQLException e) {
            logger.error("Error in UserApi.authorization", e);
            res.status(500);
        }

        return "";
    }

    /* Worked / Unstable / Unsafe */
    public static String delete(Request req, Response res) {
        int userId = convertToInt(req.params(":userId"));

        try(Connection conn = getConnection()) {
            ArrayList<Integer> musicIds = activitySelector("createdMusic", userId, conn);
            ArrayList<Integer> imagesIds = activitySelector("createdImages", userId, conn);
            ArrayList<Integer> playlistsIds = activitySelector("createdPlaylists", userId, conn);
            String sql = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, userId);

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0) {
                deleteAllData(musicIds, imagesIds, playlistsIds, req, res);

                res.status(200);
            }
            else {
                res.status(404);
            }

        }
        catch(SQLException e) {
            logger.error("Error in UserApi.delete", e);
            res.status(500);
        }

        return "";
    }

    public static String changeName(Request req, Response res) {
        String newName = req.queryParams("name");
        String userId = req.params(":userId");

        try(Connection conn = getConnection()) {
            String sql = "UPDATE users SET nickname = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, newName);
            stmt.setString(2, userId);

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0) {
                res.status(200);
            }
            else {
                res.status(404);
            }
        }
        catch(SQLException e) {
            logger.error("Error in UserApi.changeName", e);
            res.status(500);
        }

        return "";
    }

    public static String ban(Request req, Response res) {
        return "";
    }

    /* Worked / Unstable / Unsafe */
    public static String activity(Request req, Response res) {
        JsonArray jsonIds = new JsonArray();
        int userId = convertToInt(req.params(":userId"));
        String type = req.queryParams("type");

        try(Connection conn = getConnection()) {
            ArrayList<Integer> ids = activitySelector(type, userId, conn);
            for(Integer id : ids) {
                jsonIds.add(id);
            }
        }
        catch(SQLException e) {
            logger.error("Error in UserApi.activity", e);
            res.status(500);
        }

        return gson.toJson(jsonIds);
    }

    public static String subscribe(Request req, Response res) {
        return "";
    }

    public static String discard(Request req, Response res) {
        return "";
    }

    record UserData(String nickname, String email, String password, int access) {}
}