package github.otowave.otomusic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Response;

public class MusicHandler {
    private static final Logger logger = LoggerFactory.getLogger(MusicHandler.class);

    protected static int convertParamsToInt(Response response, String str) {
        int num = -1;

        try {
            num = Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            logger.error("Detected unconvertible String in id variable", e);
            response.status(400);
        }

        return num;
    }
}
