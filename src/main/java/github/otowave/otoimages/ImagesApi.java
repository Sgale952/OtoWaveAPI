package github.otowave.otoimages;

import com.google.gson.Gson;
import github.otowave.otomusic.MusicApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

public class ImagesApi extends ImagesHandler {
    private static final Logger logger = LoggerFactory.getLogger(MusicApi.class);
    private static final Gson gson = new Gson();

    public static String upload(Request req, Response res) {
        saveImageFile();
        return "";
    }

    public static String update(Request req, Response res) {
        saveImageFile();
        //Delete old file
        deleteImageFile();
        return "";
    }
}
