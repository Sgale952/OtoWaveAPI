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

        try (Connection conn = getConnection()) {
            String hashedPassword = BCrypt.hashpw(musicData.password(), BCrypt.gensalt());
            String sql = "INSERT INTO users (access, nickname, email, passwrd) " +
                    "VALUES (?, '?', '?', '?','?')";
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
        } catch (SQLException e) {
            logger.error("Error in UserApi.upload", e);
            res.status(500);
        }

        return userId;
    }

    public static String authorization(Request req, Response res) {
        try (Connection conn = getConnection()) {
            String username = req.queryParams("email");
            String password = req.queryParams("password");
            PreparedStatement stmt = conn.prepareStatement("SELECT passwrd FROM users WHERE email = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPasswordFromDB = rs.getString("passwrd");

                // Проверяем введенный пароль с хешированным паролем из базы данных
                if (BCrypt.checkpw(password, hashedPasswordFromDB)) {
                    return "Успешная авторизация!";
                } else {
                    return "Неверное имя пользователя или пароль.";
                }
            } else {
                return "Неверное имя пользователя или пароль.";
            }
        } catch (SQLException e) {
            logger.error("Error in UserApi.authorization", e);
            res.status(500);
            return null;
        }
    }


    public static String change_name(Request req, Response res) {
        try (Connection conn = getConnection()) {
            String new_name = req.queryParams("new_name");
            String user_id = req.params(":userId");

            // Подготовленный запрос для обновления имени пользователя
            String sql = "UPDATE users SET name = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Установка параметров запроса
            stmt.setString(1, new_name);
            stmt.setString(2, user_id);

            // Выполнение запроса на обновление имени пользователя
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                res.status(200);
                return "Имя пользователя успешно изменено!";
            } else {
                res.status(404);
                return "Пользователь с указанным ID не найден.";
            }
        } catch (SQLException e) {
            logger.error("Error in UserApi.change_name", e);
            res.status(500);
            return "Произошла ошибка при изменении имени пользователя.";
        }
    }
}

record UserData(int access, String nickname, String email, String password) {}