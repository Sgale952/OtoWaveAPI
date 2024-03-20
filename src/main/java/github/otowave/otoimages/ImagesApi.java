package github.otowave.otoimages;

import com.google.gson.Gson;
import github.otowave.otomusic.MusicApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.sql.*;

import static github.otowave.api.CommonUtils.convertParamsToInt;
import static github.otowave.api.DatabaseManager.getConnection;

public class ImagesApi extends ImagesHandler {
    private static final Logger logger = LoggerFactory.getLogger(MusicApi.class);
    private static final Gson gson = new Gson();

    //TODO: need tests
    public static String upload(Request req, Response res) {
        ImagesData imagesData = gson.fromJson(req.body(), ImagesData.class);
        int sourceId = convertParamsToInt(req.queryParams("musicId"));
        int imageId = 0;

        try(Connection conn = getConnection()) {
            String sql = "INSERT INTO images (uploader, file_type) VALUES (?, '?')";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setInt(1, imagesData.uploader());
            stmt.setString(2, imagesData.fileType());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            imageId = generatedKeys.getInt(1);

            PreparedStatement stmt2 = conn.prepareStatement(applyImage(imagesData.fileType(), sourceId, imageId));
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

    public static String update(Request req, Response res) {
        //saveImageFile();
        //Delete old file
        deleteImageFile();
        return "";
    }
}

record ImagesData(int uploader, String fileType, String usage) {}