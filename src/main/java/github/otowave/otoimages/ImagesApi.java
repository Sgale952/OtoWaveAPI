package github.otowave.otoimages;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.*;

import static github.otowave.api.CommonUtils.convertParamsToInt;
import static github.otowave.api.DatabaseManager.getConnection;

public class ImagesApi extends ImagesHandler {
    private static final Logger logger = LoggerFactory.getLogger(ImagesApi.class);
    private static final Gson gson = new Gson();

    //TODO: need tests
    public static String upload(Request req, Response res) {
        ImagesData imagesData = gson.fromJson(req.body(), ImagesData.class);
        int sourceId = convertParamsToInt(req.queryParams("sourceId"));
        int imageId = 0;

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO images (uploader, file_type) VALUES (?, '?')";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, convertParamsToInt(imagesData.uploader()));
            stmt.setString(2, imagesData.fileType());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            imageId = generatedKeys.getInt(1);

            PreparedStatement stmt2 = conn.prepareStatement(applyImage(imagesData.usage(), sourceId, imageId));
            stmt2.executeUpdate();
            saveImageFile(req, imageId);

            res.status(201);
        }
        catch (SQLException | IOException e) {
            logger.error("Error in ImagesApi.upload", e);
            res.status(500);
        }

        return String.valueOf(imageId);
    }

    //TODO: need tests
    public static String update(Request req, Response res) {
        try {
            int ImageId = convertParamsToInt(req.queryParams("sourceId"));
            saveImageFile(req, ImageId);

            res.status(200);
        }
        catch (IOException e) {
            logger.error("Error in ImagesApi.update", e);
            res.status(500);
        }

        return "";
    }

    public static String delete (Request req, Response res) {
        return "";
    }
}

record ImagesData(String uploader, String fileType, String usage) {}