package github.otowave.otousers;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.sql.*;

import static github.otowave.api.DatabaseManager.getConnection;

public class UsersApi {
    private static final Logger logger = LoggerFactory.getLogger(UsersApi.class);
    private static final Gson gson = new Gson();

    public static String upload(Request req, Response res) {
        UserData musicData = gson.fromJson(req.body(), UserData.class);
        String userId = "";

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO users (access, nickname, email, passwrd) " +
                    "VALUES (?, '?', '?', '?','?')";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, musicData.access());
            stmt.setString(2, musicData.nickname());
            stmt.setString(3, musicData.email());
            stmt.setString(4, musicData.password());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next()) {
                userId = generatedKeys.getString(1);
            }

            res.status(201);
        }
        catch (SQLException e) {
            logger.error("Error in UserApi.upload", e);
            res.status(500);
        }

        return userId;
    }
     public static String authorization(Request req, Response res) {
         String username = req.queryParams("email");
         String password = req.queryParams("password");

         try(Connection conn = getConnection()) {
             String sql = "SELECT * FROM users WHERE login = ? AND passwrd = ?";
             PreparedStatement stmt = conn.prepareStatement(sql);

             stmt.setString(1, username);
             stmt.setString(2, password);

             ResultSet rs = stmt.executeQuery();
             if(rs.next()) {
                 return "Успешная авторизация!";
             } else {
                 return "Неверное имя пользователя или пароль.";
             }
         }
         catch (SQLException e) {
             logger.error("Error in UserApi.authorization", e);
            res.status(500);
         }

         return "";
     }
    public static String changeName(Request req, Response res) {
        String newName = req.queryParams("new_name");
        String userid = req.params(":userId");

        try(Connection conn = getConnection()) {
            String sql = "UPDATE users * SET name = "+ newName + "WHERE user_id =" + userid;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.executeUpdate();

            res.status(201);
        }
        catch (SQLException e) {
            logger.error("Error in UserApi.changeName", e);
            res.status(500);
        }

        return "";
    }
}

record UserData(int access, String nickname, String email, String password) {}