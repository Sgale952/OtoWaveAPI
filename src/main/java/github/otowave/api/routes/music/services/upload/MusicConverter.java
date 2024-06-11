package github.otowave.api.routes.music.services.upload;

import github.otowave.api.configuration.StaticContentDirs;
import github.otowave.api.exceptions.FfmpegExecutionException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class MusicConverter {
    private final String MUSIC_DIR = StaticContentDirs.MUSIC_DIR.getDir();

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
            deleteUnconvertedFile(inputFile);
        }
        catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
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
        Process process = builder.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new FfmpegExecutionException(inputFile, exitCode);
        }
    }

    private void deleteUnconvertedFile(String file) {
        new File(file).delete();
    }
}
