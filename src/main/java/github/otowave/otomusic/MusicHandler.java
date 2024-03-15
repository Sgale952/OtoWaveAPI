package github.otowave.otomusic;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

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

    protected static void deleteAudioFile(int musicId) throws IOException {
        Path dir = Path.of("/home/otowave/data/songs/"+musicId);
        Files.walk(dir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    protected static void saveAudioFile(Request request, int musicId) throws IOException {
        InputStream inputStream = request.raw().getInputStream();
        String fileName = musicId + request.queryParams("fileType");
        String dir = "/home/otowave/data/songs/" + musicId;

        Path dirPath = Path.of(dir);
        Files.createDirectory(dirPath);

        Path filePath = dirPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

        convertAudioFileToAac(musicId, request.queryParams("fileType"));
    }

    private static void convertAudioFileToAac( int musicId, String fileType) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("/usr/bin/ffprobe");
        String inputFile = musicId + fileType;
        String outputFile = musicId + ".aac";

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputFile)
                .overrideOutputFiles(true)
                .addOutput(outputFile)
                .setFormat(".aac")
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        //Check performance. Can be replaced by executor.createJob(builder).run();
        executor.createTwoPassJob(builder).run();

        trimAacFile();
    }

    private static void trimAacFile() {

    }

}
