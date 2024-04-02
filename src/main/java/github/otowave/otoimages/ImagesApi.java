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
import static github.otowave.otoimages.ImagesHandler.*;

public class ImagesApi {
    private static final Logger logger = LoggerFactory.getLogger(ImagesApi.class);
    private static final Gson gson = new Gson();

    /* Worked / Unstable / Unsafe */
    public static String upload(Request req, Response res) {
        ImageData imageData = gson.fromJson(req.body(), ImageData.class);
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

            apply(imageData, conn);
            saveImageFile(req, imageId);

            res.status(201);
        }
        catch(SQLException | ServletException | IOException e) {
            logger.error("Error in ImagesApi.upload", e);
            res.status(500);
        }

        return imageId;
    }

    record ImageData(String imageType, String imageId, int prevImageId, String sourceId) {}
}
