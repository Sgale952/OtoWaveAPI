package github.otowave.otomusic;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final String MUSIC_DIR = "/home/otowave/data/songs/";
    private static final String FFMPEG_PATH = "/usr/bin/ffmpeg";

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

        convertAudioFileToAac(musicId, getFileExtension(musicPart));
    }

    private static void convertAudioFileToAac(String musicId, String fileType) throws IOException {
        FFmpeg ffmpeg = new FFmpeg(FFMPEG_PATH);
        String inputFile = MUSIC_DIR + musicId + "/" + musicId + fileType;
        String outputFile = MUSIC_DIR + musicId + "/" + musicId + ".aac";

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputFile)
                .overrideOutputFiles(true)
                .addOutput(outputFile)
                .setAudioCodec("aac")
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
        executor.createJob(builder).run();

        deleteOldAudioFile(inputFile);

        //trimAacFile(musicId, outputFile);
    }

    //Not worked
    private static void trimAacFile(int musicId, String inputFile) throws IOException {
        FFmpeg ffmpeg = new FFmpeg(FFMPEG_PATH);
        String outputDir = MUSIC_DIR + musicId + "/";

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputFile)
                .overrideOutputFiles(true)
                .addOutput(outputDir+"output_%03d.ts")
                .setFormat("mpegts")
                .setAudioCodec("copy")
                .setAudioBitRate(128000)
                .setStartOffset(0, TimeUnit.SECONDS)
                .addExtraArgs("-hls_time", "10")
                .addExtraArgs("-hls_list_size", "0")
                .addExtraArgs("-hls_segment_filename", "output_%03d.ts")
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
        executor.createJob(builder).run();
    }

    private static void deleteOldAudioFile(String dir) {
        File audioFile = new File(dir);
        audioFile.delete();
    }
}