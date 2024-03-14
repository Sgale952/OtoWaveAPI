package github.otowave.otomusic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

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

    protected static void saveAudioFile(Request request, int musicId) throws IOException {
        InputStream inputStream = request.raw().getInputStream();
        String fileName = musicId + request.queryParams("fileType");
        String dir = "/home/otowave/data/songs/" + musicId;

        Path dirPath = Path.of(dir);
        Files.createDirectory(dirPath);

        Path filePath = dirPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        convertAudioFileToAac(filePath, fileName);
    }

    //Use ffmpeg-wrapper?
    private static void convertAudioFileToAac(Path filePath, String inputFile) {
        String outputFile = inputFile + ".aac";

    }
    private static void trimAacFile() {

    }

}
