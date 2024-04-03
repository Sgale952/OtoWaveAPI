package github.otowave.otomusic;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import spark.Request;
import spark.utils.IOUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static github.otowave.api.UploadHelper.*;

public class MusicHandler {
    //private static final String MUSIC_DIR = "/home/otowave/data/music/";
    private static final String MUSIC_DIR = "D:\\i\\";
    //private static final String FFMPEG_PATH = "/usr/bin/ffmpeg";
    private static final String FFMPEG_PATH = "C:\\Program Files\\FFmpeg\\ffmpeg.exe";

    static LocalDate convertDailyRandomCookieToDate(String year, String month, String day) {
        int convertedYear = convertToInt(year);
        int convertedMonth = convertToInt(month);
        int convertedDay = convertToInt(day);

        return LocalDate.of(convertedYear, convertedMonth, convertedDay);
    }

    static void deleteAudioFile(int musicId) throws IOException {
        Path dir = Path.of(MUSIC_DIR + musicId);
        try (Stream<Path> musicFiles = Files.walk(dir)) {
            musicFiles
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        }
    }

    static void saveAudioFile(Request req, String musicId) throws IOException, ServletException {
        Part musicPart = getStaticFilePart(req, "audio");
        String fileName = musicId+getFileExtension(musicPart);
        String songDir = MUSIC_DIR + musicId;

        Path dirPath = Path.of(songDir);
        Files.createDirectory(dirPath);

        try(OutputStream outputStream = new FileOutputStream(songDir + "/" + fileName)) {
            IOUtils.copy(musicPart.getInputStream(), outputStream);
        }

        convertToM3u8(musicId, getFileExtension(musicPart));
    }

    private static void convertToM3u8(String musicId, String fileExtension) throws IOException {
        FFmpeg ffmpeg = new FFmpeg(FFMPEG_PATH);
        String inputFile = MUSIC_DIR + musicId + "/" + musicId + fileExtension;
        String outputFile = MUSIC_DIR + musicId + "/" + musicId + ".m3u8";

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputFile)
                .overrideOutputFiles(true)
                .addOutput(outputFile)
                .setAudioCodec("aac")
                .setFormat("hls")
                .setStartOffset(0, TimeUnit.SECONDS)
                .addExtraArgs("-hls_time", "10")
                .addExtraArgs("-hls_list_size", "0")
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
        executor.createJob(builder).run();

        deleteOldAudioFile(inputFile);
    }

    private static void deleteOldAudioFile(String dir) {
        File audioFile = new File(dir);
        audioFile.delete();
    }
}