package github.otowave.otoimages;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.*;

import static github.otowave.api.UploadHelper.convertToInt;
import static github.otowave.api.DatabaseManager.getConnection;

public class ImagesApi extends ImagesHandler {
    private static final Logger logger = LoggerFactory.getLogger(ImagesApi.class);
    private static final Gson gson = new Gson();

    /* Worked / Unstable / Unsafe */
    public static String upload(Request req, Response res) {
        int uploaderId = convertToInt(req.params(":userId"));
        String imageId = "";

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO images (uploader_id) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, uploaderId);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next()) {
                imageId = generatedKeys.getString(1);
            }

            saveImageFile(req, imageId);

            res.status(201);
        }
        catch(SQLException | ServletException | IOException e) {
            logger.error("Error in ImagesApi.upload", e);
            res.status(500);
        }

        return imageId;
    }

    /* Worked / Unstable / Unsafe */
    public static String apply(Request req, Response res) {
        ImageData imageData = gson.fromJson(req.body(), ImageData.class);

        try(Connection conn = getConnection()) {
            String sql = applyImage(imageData.imageType());
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, imageData.imageId());
            stmt.setString(2, imageData.sourceId());
            stmt.executeUpdate();

            if(imageData.prevImageId() > 4) {
                deleteImageFile(imageData.prevImageId());
            }

            res.status(200);
        }
        catch (SQLException | IOException e) {
            logger.error("Error in ImagesApi.upload", e);
            res.status(500);
        }

        return "";
    }

    record ImageData(String imageType, String imageId, int prevImageId, String sourceId) {}
}
