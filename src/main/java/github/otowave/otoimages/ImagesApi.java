package github.otowave.otoimages;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.sql.*;

import static github.otowave.api.CommonUtils.convertToInt;
import static github.otowave.api.DatabaseManager.getConnection;

public class ImagesApi extends ImagesHandler {
    private static final Logger logger = LoggerFactory.getLogger(ImagesApi.class);
    private static final Gson gson = new Gson();

    //TODO: need tests
    public static String upload(Request req, Response res) {
        String imageType = req.queryParams("imageType");
        int sourceId = convertToInt(req.queryParams("sourceId"));
        int uploaderId = convertToInt(req.queryParams("uploaderId"));
        int imageId = 0;

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO images (uploader) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, uploaderId);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                imageId = generatedKeys.getInt(1);
            }

            String sql2 = applyImage(imageType, imageId, sourceId);
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.executeUpdate();

            saveImageFile(req, imageId);

            res.status(201);
        }
        catch (SQLException e) {
            logger.error("Error in ImagesApi.upload", e);
            res.status(500);
        }

        return String.valueOf(imageId);
    }

    public static String update(Request req, Response res) {
        return "";
    }

    public static String delete(Request req, Response res) {
        return "";
    }
}
