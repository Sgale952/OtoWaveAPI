package github.otowave.otomusic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import github.otowave.otoimages.ImagesApi;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static github.otowave.api.UploadHelper.convertToInt;
import static github.otowave.api.DatabaseManager.getConnection;
import static github.otowave.api.UploadHelper.haveAttribute;
import static github.otowave.otomusic.MusicHandler.*;

public class MusicApi {
    private static final Logger logger = LoggerFactory.getLogger(MusicApi.class);
    private static final Gson gson = new Gson();

    //TODO: need tests
    public static String allData(Request req, Response res) {
        JsonObject jsonOutput = new JsonObject();
        String musicId = req.params(":musicId");

        try(Connection conn = getConnection()) {
            String sql = "SELECT * FROM music WHERE music_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, musicId);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                jsonOutput.addProperty("musicId", rs.getInt("music_id"));
                jsonOutput.addProperty("author", rs.getInt("author_id"));
                jsonOutput.addProperty("coverId", rs.getInt("cover_id"));
                jsonOutput.addProperty("title", rs.getString("title"));
                jsonOutput.addProperty("genre", rs.getString("genre"));
                jsonOutput.addProperty("econtent", rs.getInt("econtent"));
                jsonOutput.addProperty("likes", rs.getInt("likes"));
                jsonOutput.addProperty("listens", rs.getInt("listens"));
                jsonOutput.addProperty("uploaded", String.valueOf(rs.getTimestamp("uploaded")));
            }

            res.status(200);
        }
        catch(SQLException e) {
            logger.error("Error in MusicApi.allData", e);
            res.status(500);
        }

        return gson.toJson(jsonOutput);
    }

    /* Worked (slow) / Unstable / Unsafe */
    public static String upload(Request req, Response res) {
        String title = req.queryParams("title");
        String genre = req.queryParams("genre");
        int eContent = convertToInt(req.queryParams("eContent"));
        int uploaderId = convertToInt(req.params(":userId"));
        String musicId = "";

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO music (author_id, title, genre, econtent) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, uploaderId);
            stmt.setString(2, title);
            stmt.setString(3, genre);
            stmt.setInt(4, eContent);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next()) {
                musicId = generatedKeys.getString(1);
                saveAudioFile(req, musicId);
            }
            else {
                throw new SQLException("musicId was not generated");
            }

            res.status(201);
        }
        catch(SQLException | ServletException | IOException e) {
            logger.error("Error in MusicApi.upload", e);
            res.status(500);
        }

        return musicId;
    }

    //TODO: need tests
    public static String update(Request req, Response res) {
        MusicData musicData = gson.fromJson(req.body(), MusicData.class);
        int musicId = convertToInt(req.params(":musicId"));

        try(Connection conn = getConnection()) {
            String sql = "UPDATE music SET cover_id = ?, title = ?, genre = ?, econtent = ?  WHERE music_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, musicData.coverId());
            stmt.setString(2, musicData.title());
            stmt.setString(3, musicData.genre());
            stmt.setInt(4, musicData.eContent());
            stmt.setInt(5, musicId);
            stmt.executeUpdate();

            res.status(200);
        }
        catch(NumberFormatException e) {
            logger.error("Detected unconvertible String in id variable", e);
            res.status(400);
        }
        catch(SQLException e) {
            logger.error("Error in MusicApi.update", e);
            res.status(500);
        }

        return "";
    }

    /* Worked / Unstable / Unsafe */
    public static String delete(Request req, Response res) {
        int musicId = haveAttribute("musicId", req)? req.attribute("musicId") : convertToInt(req.params(":musicId"));

        try(Connection conn = getConnection()) {
            int imageId = getCoverId(musicId, conn);
            String sql = "DELETE FROM music WHERE music_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, musicId);

            int rowsAffected = stmt.executeUpdate();
            if(rowsAffected > 0) {
                deleteAudio(musicId);
                ImagesApi.delete(imageId);

                res.status(200);
            }
            else {
                res.status(404);
            }
        }
        catch(SQLException | IOException e) {
            logger.error("Error in MusicApi.delete", e);
            res.status(500);
        }

        return "";
    }

    //Im just silly kitten don't blame me
    //TODO: need tests
    public static String dailyRandom(Request req, Response res) {
        LocalDate cookieDate = convertDailyRandomCookieToDate(req.cookie("year"), req.cookie("month"), req.cookie("day"));
        LocalDate currentDate = LocalDate.now();

        if(currentDate.isEqual(cookieDate)) {
            res.status(200);
            return "";
        }

        ArrayList<Integer> musicIds = new ArrayList<>(26);
        try(Connection conn = getConnection()) {
            String sql = "SELECT music_id FROM music ORDER BY RAND() LIMIT 25";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                musicIds.add(rs.getInt("music_id"));
            }

            res.status(205);
        }
        catch(SQLException e) {
            logger.error("Error in MusicApi.dailyRandom", e);
            res.status(500);
        }

        //Bruh
        res.cookie("musicIds", musicIds.toString());
        return "";
    }

    /* Worked / Unstable / Unsafe */
    public static String search(Request req, Response res) {
        Map<String, ArrayList<Integer>> resultIds = new HashMap<>();
        String searchPhrase = "%" + req.queryParams("phrase") + "%";

        try(Connection conn = getConnection()) {
            String sql = "SELECT music_id, null as playlist_id, null as user_id FROM music WHERE title LIKE ? " +
                         "UNION SELECT null as music_id, playlist_id, null as user_id FROM playlists WHERE title LIKE ? " +
                         "UNION SELECT null as music_id, null as playlist_id, user_id FROM users WHERE nickname LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, searchPhrase);
            stmt.setString(2, searchPhrase);
            stmt.setString(3, searchPhrase);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int musicId = rs.getInt("music_id");
                int playlistId = rs.getInt("playlist_id");
                int userId = rs.getInt("user_id");

                if (musicId != 0) {
                    resultIds.computeIfAbsent("music", k -> new ArrayList<>()).add(musicId);
                }
                if (playlistId != 0) {
                    resultIds.computeIfAbsent("playlists", k -> new ArrayList<>()).add(playlistId);
                }
                if (userId != 0) {
                    resultIds.computeIfAbsent("users", k -> new ArrayList<>()).add(userId);
                }
            }

            res.status(200);
        }
        catch(SQLException e) {
            logger.error("Error in MusicApi.search", e);
            res.status(500);
        }

        return gson.toJson(resultIds);
    }

    /* Worked / Unstable / Unsafe */
    public static String genres(Request req, Response res) {
        Map<Integer, String> genres = new LinkedHashMap<>();

        try(Connection conn = getConnection()) {
            String sql = "SELECT genre_id FROM genres";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            for(int i = 0; rs.next(); i++) {
                genres.put(i, rs.getString("genre_id"));
            }

            res.status(200);
        }
        catch(SQLException e) {
            logger.error("Error in MusicApi.genres", e);
            res.status(500);
        }

        return gson.toJson(genres);
    }

    //TODO: need tests
    public static String topPerMonth(Request req, Response res) {
        Map<Integer, String> musicIds = new LinkedHashMap<>();
        String sortBy = req.contextPath().equals("/recent") ? "uploaded" : "listens";

        try(Connection conn = getConnection()) {
            String sql = "SELECT music_id, genre FROM music WHERE MONTH(uploaded) = MONTH(CURRENT_DATE()) AND YEAR(uploaded) = YEAR(CURRENT_DATE()) ORDER BY " +
                         sortBy + " DESC LIMIT 100";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    musicIds.put(rs.getInt("music_id"), rs.getString("genre"));
                }

            res.status(200);
        }
        catch(SQLException e) {
            logger.error("Error in MusicApi.topPerMonth", e);
            res.status(500);
        }

        return gson.toJson(musicIds);
    }

    //TODO: need tests
    public static String like(Request req, Response res) {
        int userId = convertToInt(req.params(":userId"));
        int musicId = convertToInt(req.queryParams("musicId"));
        UserMusicRelation data = new UserMusicRelation(userId, musicId);

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO likedMusic (user_id, music_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.userId());
            stmt.setInt(2, data.musicId());
            stmt.executeUpdate();

            res.status(201);
        }
        catch(NumberFormatException e) {
            logger.error("Detected unconvertible String in id variable", e);
            res.status(400);
        }
        catch(SQLException e) {
            logger.error("Error in MusicApi.like", e);
            res.status(500);
        }

        return "";
    }

    //TODO: need tests
    public static String discard(Request req, Response res) {
        int userId = convertToInt(req.params(":userId"));
        int musicId = convertToInt(req.queryParams("musicId"));
        UserMusicRelation data = new UserMusicRelation(userId, musicId);

        try(Connection conn = getConnection()) {
            String sql = "DELETE FROM likedMusic WHERE user_id = ? AND music_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.userId());
            stmt.setInt(2, data.musicId());
            stmt.executeUpdate();

            res.status(201);
        }
        catch(NumberFormatException e) {
            logger.error("Detected unconvertible String in id variable", e);
            res.status(400);
        }
        catch(SQLException e) {
            logger.error("Error in MusicApi.discard", e);
            res.status(500);
        }

        return "";
    }

    //TODO: need tests
    public static String updateLikes(Request req, Response res) {
        int musicId = convertToInt(req.params(":musicId"));

        try(Connection conn = getConnection()) {
            String sql = "UPDATE music SET likes = likes + 1 WHERE music_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, musicId);
            stmt.executeUpdate();

            res.status(201);
        }
        catch(NumberFormatException e) {
            logger.error("Detected unconvertible String in id variable", e);
            res.status(400);
        }
        catch(SQLException e) {
            logger.error("Error in MusicApi.updateLikes", e);
            res.status(500);
        }

        return "";
    }

    //TODO: need tests
    public static String updateListens(Request req, Response res) {
        int musicId = convertToInt(req.params(":musicId"));

        try(Connection conn = getConnection()) {
            String sql = "UPDATE music SET listens = listens + 1 WHERE music_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, musicId);
            stmt.executeUpdate();

            res.status(201);
        }
        catch(NumberFormatException e) {
            logger.error("Detected unconvertible String in id variable", e);
            res.status(400);
        }
        catch(SQLException e) {
            logger.error("Error in MusicApi.updateLikes", e);
            res.status(500);
        }

        return "";
    }

    record UserMusicRelation(int userId, int musicId) {}
    record MusicData(int authorId, int coverId, String title, String genre, int eContent) {}
}