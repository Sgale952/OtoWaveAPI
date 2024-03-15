package github.otowave.otomusic;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static github.otowave.api.DatabaseManager.getConnection;

public class MusicApi extends MusicHandler {
    private static final Logger logger = LoggerFactory.getLogger(MusicApi.class);
    private static final Gson gson = new Gson();

    //TODO: need tests
    public static String allData(Request request, Response response) {
        JsonObject jsonOutput = new JsonObject();
        String musicId = request.params(":musicId");

        try(Connection conn = getConnection()) {
            String sql = "SELECT * FROM music WHERE music_id = " + musicId;
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                jsonOutput.addProperty("music_id", rs.getInt("music_id"));
                jsonOutput.addProperty("author", rs.getInt("author"));
                jsonOutput.addProperty("title", rs.getString("title"));
                jsonOutput.addProperty("econtent", rs.getInt("econtent"));
                jsonOutput.addProperty("genre", rs.getString("genre"));
                jsonOutput.addProperty("likes", rs.getInt("likes"));
                jsonOutput.addProperty("listens", rs.getInt("listens"));
                jsonOutput.addProperty("uploaded", String.valueOf(rs.getTimestamp("uploaded")));
                jsonOutput.addProperty("duration", String.valueOf(rs.getTime("duration")));
                jsonOutput.addProperty("cover_id", rs.getInt("cover_id"));
            }

            response.status(200);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.allData", e);
            response.status(500);
        }

        return gson.toJson(jsonOutput);
    }

    //Add and check audio duration
    //TODO: need tests
    public static String upload(Request request, Response response) {
        MusicData musicData = gson.fromJson(request.body(), MusicData.class);
        int musicId;

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO music (author, title, econtent, genre, cover_id) " +
                    "VALUES (?, '?', ?, '?', ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, musicData.authorId());
            stmt.setString(2, musicData.title());
            stmt.setInt(3, musicData.eContent());
            stmt.setString(4, musicData.genre());
            stmt.setInt(5, musicData.coverId());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            musicId = generatedKeys.getInt(1);
            saveAudioFile(request, musicId);

            response.status(201);
        }
        catch (SQLException | IOException e) {
            logger.error("Error in MusicApi.upload", e);
            response.status(500);
        }

        return "";
    }

    //TODO: need tests
    public static String update(Request request, Response response) {
        MusicData musicData = gson.fromJson(request.body(), MusicData.class);
        int musicId = convertParamsToInt(request.queryParams("musicId"));

        try(Connection conn = getConnection()) {
            String sql = "UPDATE music SET author = ?, title = '?', econtent = ?, genre = '?', cover_id = ? WHERE music_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, musicData.authorId());
            stmt.setString(2, musicData.title());
            stmt.setInt(3, musicData.eContent());
            stmt.setString(4, musicData.genre());
            stmt.setInt(5, musicData.coverId());
            stmt.setInt(6, musicId);
            stmt.executeUpdate();

            response.status(200);
        }
        catch (NumberFormatException e) {
            logger.error("Detected unconvertible String in id variable", e);
            response.status(400);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.update", e);
            response.status(500);
        }

        return "";
    }

    //TODO: need tests
    public static String delete(Request request, Response response) {
        int musicId = convertParamsToInt(request.queryParams("musicId"));

        try(Connection conn = getConnection()) {
            String sql = "DELETE FROM music WHERE music_id = " + musicId;
            PreparedStatement stmt = conn.prepareStatement(sql);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                deleteAudioFile(musicId);
                //deleteImageFile(musicId);
                response.status(200);
            }
            else {
                response.status(404);
            }
        }
        catch (NumberFormatException e) {
            logger.error("Detected unconvertible String in id variable", e);
            response.status(400);
        }
        catch (SQLException | IOException e) {
            logger.error("Error in MusicApi.delete", e);
            response.status(500);
        }

        return "";
    }

    //Im just silly kitten don't blame me
    //TODO: need tests
    public static String dailyRandom(Request request, Response response) {
        LocalDate cookieDate = convertDailyRandomCookieToDate(request.cookie("year"), request.cookie("month"), request.cookie("day"));
        LocalDate currentDate = LocalDate.now();

        if (currentDate.isEqual(cookieDate)) {
            response.status(200);
            return "";
        }

        ArrayList<Integer> musicIds = new ArrayList<>(26);
        try(Connection conn = getConnection()) {
            String sql = "SELECT music_id FROM music ORDER BY RAND() LIMIT 25";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                musicIds.add(rs.getInt("music_id"));
            }

            response.status(205);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.dailyRandom", e);
            response.status(500);
        }

        //Bruh
        response.cookie("musicIds", musicIds.toString());
        return "";
    }


    public static String search(Request request, Response response) {
        Map<String, Integer> resultIds = new LinkedHashMap<>();
        String searchPhrase = request.queryParams("%"+"phrase"+"%");

        try(Connection conn = getConnection()) {
            //need null?
            String sql = "SELECT music_id, null as playlist_id, null as user_id FROM songs WHERE title LIKE '?' " +
                         "UNION SELECT null as music_id, playlist_id, null as user_id FROM playlists WHERE name LIKE '?' " +
                         "UNION SELECT null as music_id, null as playlist_id, user_id FROM users WHERE username LIKE '?'";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, searchPhrase);
            stmt.setString(2, searchPhrase);
            stmt.setString(3, searchPhrase);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("music_id");
                String table = rs.getString("type");

                resultIds.put(table, id);
            }

            response.status(200);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.search", e);
            response.status(500);
        }

        return gson.toJson(resultIds);
    }

    //TODO: need tests
    public static String topPerMonth(Request request, Response response) {
        Map<Integer, String> musicIds = new LinkedHashMap<>();
        String sortBy = request.contextPath().equals("/recent") ? "uploaded" : "listens";

        try(Connection conn = getConnection()) {
            String sql = "SELECT music_id, genre FROM music WHERE MONTH(uploaded) = MONTH(CURRENT_DATE()) AND YEAR(uploaded) = YEAR(CURRENT_DATE()) ORDER BY " +
                         sortBy + " DESC LIMIT 100";
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    musicIds.put(rs.getInt("music_id"), rs.getString("genre"));
                }

            response.status(200);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.topPerMonth", e);
            response.status(500);
        }

        return gson.toJson(musicIds);
    }

    //TODO: need tests
    public static String like(Request request, Response response) {
        int userId = convertParamsToInt(request.params(":userId"));
        int musicId = convertParamsToInt(request.queryParams("musicId"));
        UserMusicRelation data = new UserMusicRelation(userId, musicId);

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO likedSongs (user_id, music_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.userId());
            stmt.setInt(2, data.musicId());
            stmt.executeUpdate();

            response.status(201);
        }
        catch (NumberFormatException e) {
            logger.error("Detected unconvertible String in id variable", e);
            response.status(400);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.like", e);
            response.status(500);
        }

        return "";
    }

    //TODO: need tests
    public static String discard(Request request, Response response) {
        int userId = convertParamsToInt(request.params(":userId"));
        int musicId = convertParamsToInt(request.queryParams("musicId"));
        UserMusicRelation data = new UserMusicRelation(userId, musicId);

        try(Connection conn = getConnection()) {
            String sql = "DELETE FROM likedSongs WHERE user_id = ? AND song_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.userId());
            stmt.setInt(2, data.musicId());
            stmt.executeUpdate();

            response.status(201);
        }
        catch (NumberFormatException e) {
            logger.error("Detected unconvertible String in id variable", e);
            response.status(400);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.discard", e);
            response.status(500);
        }

        return "";
    }

    //TODO: need tests
    public static String updateLikes(Request request, Response response) {
        int musicId = convertParamsToInt(request.params(":musicId"));
        UserMusicRelation data = new UserMusicRelation(-1, musicId);


        try(Connection conn = getConnection()) {
            String sql = "UPDATE music SET likes = likes + 1 WHERE music_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.musicId());
            stmt.executeUpdate();

            response.status(201);
        }
        catch (NumberFormatException e) {
            logger.error("Detected unconvertible String in id variable", e);
            response.status(400);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.updateLikes", e);
            response.status(500);
        }

        return "";
    }

    //TODO: need tests
    public static String updateListens(Request request, Response response) {
        int musicId = convertParamsToInt(request.params(":musicId"));
        UserMusicRelation data = new UserMusicRelation(-1, musicId);


        try(Connection conn = getConnection()) {
            String sql = "UPDATE music SET listens = listens + 1 WHERE music_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, data.musicId());
            stmt.executeUpdate();

            response.status(201);
        }
        catch (NumberFormatException e) {
            logger.error("Detected unconvertible String in id variable", e);
            response.status(400);
        }
        catch (SQLException e) {
            logger.error("Error in MusicApi.updateLikes", e);
            response.status(500);
        }

        return "";
    }
}

record UserMusicRelation(int userId, int musicId) {}
record MusicData(int authorId, String title, int eContent, String genre, int coverId) {}