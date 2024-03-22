package github.otowave.otoimages;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.*;

import static github.otowave.api.CommonUtils.convertToInt;
import static github.otowave.api.DatabaseManager.getConnection;

public class ImagesApi extends ImagesHandler {
    private static final Logger logger = LoggerFactory.getLogger(ImagesApi.class);

    /* Worked / Unstable / Unsafe */
    public static String upload(Request req, Response res) {
        String imageType = req.queryParams("imageType");
        String sourceId = req.queryParams("sourceId");
        String prevImageId = req.queryParams("prevImageId");
        int uploaderId = convertToInt(req.params(":userId"));
        int imageId = 0;

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO images (uploader) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, uploaderId);
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next()) {
                imageId = generatedKeys.getInt(1);
            }

            String sql2 = applyImage(imageType, imageId, sourceId);
            PreparedStatement stmt2 = conn.prepareStatement(sql2);
            stmt2.executeUpdate();

            saveImageFile(req, imageId);

            if(prevImageId != null) {
                deleteImageFile(prevImageId);
            }

            res.status(201);
        }
        catch(SQLException | ServletException | IOException e) {
            logger.error("Error in ImagesApi.upload", e);
            res.status(500);
        }

        return String.valueOf(imageId);
    }
}
