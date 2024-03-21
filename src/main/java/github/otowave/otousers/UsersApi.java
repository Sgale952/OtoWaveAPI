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
        UserData musicData = gson.fromJson(req.body(), UserData.class);
        int userId = 0;

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO users (access, login, nickname, email, password) " +
                    "VALUES (?, '?', '?', '?','?')";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, musicData.access());
            stmt.setString(2,musicData.login());
            stmt.setString(3, musicData.nickname());
            stmt.setString(4, musicData.email());
            stmt.setString(5, musicData.password());
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
     public static String authorization (Request req, Response res){
         try(Connection conn = getConnection()) {
             String username = req.queryParams("login");
             String password = req.queryParams("password");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?");
             stmt.setString(1, username);
             stmt.setString(2, password);
             ResultSet rs = stmt.executeQuery();
             if (rs.next()) {
                 return "Успешная авторизация!";
             } else {
                 return "Неверное имя пользователя или пароль.";
             }
         }
         catch (SQLException e){
             logger.error("Error in UserApi.authorization", e);
            res.status(500);
            return null;
         }

     }
    public static String change_name (Request req, Response res){
        try(Connection conn = getConnection()){
            String chengename = req.queryParams("new_name");
            String userid = req.queryParams(":userId");
            PreparedStatement stmt = conn.prepareStatement("UPDATE users * SET name = "+ chengename + "WHERE user_id =" + userid);
            res.status(201);
        }
        catch (SQLException e){
            logger.error("Error in UserApi.changename", e);
            res.status(500);

        }
        return null;
    }



}

record UserData(int access,String login, String nickname, String email, String password) {}
