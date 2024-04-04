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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static github.otowave.api.UploadHelper.*;

public class MusicHandler {
    //private static final String MUSIC_DIR = "/home/otowave/data/music/";
    private static final String MUSIC_DIR = "D:\\i\\";
    //private static final String FFMPEG_PATH = "/usr/bin/ffmpeg";
    private static final String FFMPEG_PATH = "C:\\Program Files\\FFmpeg\\ffmpeg.exe";

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

    private static void convertAudioFileToAac(String musicId, String fileExtension) throws IOException {
        FFmpeg ffmpeg = new FFmpeg(FFMPEG_PATH);
        String inputFile = getAudioFilePath(musicId, fileExtension);
        String outputFile = getAudioFilePath(musicId, ".aac");

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputFile)
                .overrideOutputFiles(true)
                .addOutput(outputFile)
                .setAudioCodec("aac")
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
        executor.createJob(builder).run();

        deleteOldAudioFile(inputFile);

        trimAacFile(musicId, outputFile);
    }

    private static void trimAacFile(String musicId, String inputFile) throws IOException {
        FFmpeg ffmpeg = new FFmpeg(FFMPEG_PATH);
        String outputFile = getAudioFilePath(musicId, ".m3u8");

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

    private static String getAudioFilePath(String musicId, String fileExtension) {
        return MUSIC_DIR + musicId + "/" + musicId + fileExtension;
    }

    private static void deleteOldAudioFile(String dir) {
        File audioFile = new File(dir);
        audioFile.delete();
    }

    static void deleteAudio(int musicId) throws IOException {
        Path dir = Path.of(MUSIC_DIR + musicId);
        try (Stream<Path> musicFiles = Files.walk(dir)) {
            musicFiles
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    static int getCoverId(int musicId, Connection conn) throws SQLException {
        String sql = "SELECT cover_id FROM music WHERE music_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, musicId);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            return rs.getInt("cover_id");
        }

        throw new SQLException("Image not found");
    }

    static LocalDate convertDailyRandomCookieToDate(String year, String month, String day) {
        int convertedYear = convertToInt(year);
        int convertedMonth = convertToInt(month);
        int convertedDay = convertToInt(day);

        return LocalDate.of(convertedYear, convertedMonth, convertedDay);
    }

    static boolean haveMusicIdAttribute(Request req) {
        for (String attr : req.attributes()) {
            if (Objects.equals(attr, "musicId")) {
                return true;
            }
        }
        return false;
    }
}