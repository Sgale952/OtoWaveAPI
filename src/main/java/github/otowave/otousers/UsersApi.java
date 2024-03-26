package github.otowave.otousers;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

import static github.otowave.api.DatabaseManager.getConnection;

public class UsersApi {
    private static final Logger logger = LoggerFactory.getLogger(UsersApi.class);
    private static final Gson gson = new Gson();

    public static String upload(Request req, Response res) {
        UserData musicData = gson.fromJson(req.body(), UserData.class);
        String userId = "";

        try(Connection conn = getConnection()) {
            String hashedPassword = BCrypt.hashpw(musicData.password(), BCrypt.gensalt());
            String sql = "INSERT INTO users (access, nickname, email, passwrd) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, musicData.access());
            stmt.setString(2, musicData.nickname());
            stmt.setString(3, musicData.email());
            stmt.setString(4, hashedPassword);
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

    public static String authorization(Request req, Response res) {
        String email = req.queryParams("email");
        String password = req.queryParams("password");

        try(Connection conn = getConnection()) {
            String sql = "SELECT passwrd FROM users WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                String hashedPasswordFromDB = rs.getString("passwrd");
                if(BCrypt.checkpw(password, hashedPasswordFromDB)) {
                    res.status(200);
                }
                else {
                 res.status(401);
                }
            }
            else {
                res.status(404);
            }

        } catch(SQLException e) {
            logger.error("Error in UserApi.authorization", e);
            res.status(500);
        }

        return "";
    }

    public static String changeName(Request req, Response res) {
        String new_name = req.queryParams("new_name");
        String userId = req.params(":userId");

        try(Connection conn = getConnection()) {
            String sql = "UPDATE users SET name = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, new_name);
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
}

record UserData(int access, String nickname, String email, String password) {}