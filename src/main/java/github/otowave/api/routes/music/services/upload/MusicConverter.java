package github.otowave.api.routes.music.services.upload;

import github.otowave.api.configuration.StaticContentDirs;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class MusicConverter {
    private final String MUSIC_DIR = StaticContentDirs.MUSIC_DIR.getDir();

    public MusicConverter() {
    }

    public Mono<Void> prepareToHls(int musicID) {
        return Mono.fromCallable(() -> {
            String inputFile = getFilePath(musicID, "");
            String outputFile = getFilePath(musicID, ".m3u8");
            executeFfmpeg(inputFile, outputFile);
            return null;
        });
    }

    private String getFilePath(int musicID, String extension) {
        String id = String.valueOf(musicID);
        return Paths.get(MUSIC_DIR, id, id + extension).toString();
    }

    private void executeFfmpeg(String inputFile, String outputFile) {
        try {
            runProcessBuilder(inputFile, outputFile);
            //TODO: Deletes the loaded file too early. Need to wait for FFmpeg to finish but waitFor() stops FFmpeg process
            //deleteUnconvertedFile(inputFile);
        }
        catch (Exception e) {
            //TODO: handle exception
        }
    }

    private void runProcessBuilder(String inputFile, String outputFile) throws IOException, InterruptedException {
        String tsFilePattern = inputFile + "_%d.ts";
        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg",
                "-i", inputFile,
                "-y",
                "-c:a", "aac",
                "-f", "segment",
                "-segment_time", "10",
                "-segment_list", outputFile,
                tsFilePattern
        );
        builder.start();
    }

    private void deleteUnconvertedFile(String file) {
        new File(file).delete();
    }
}
