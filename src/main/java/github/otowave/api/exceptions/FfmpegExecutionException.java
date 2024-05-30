package github.otowave.api.exceptions;

public class FfmpegExecutionException extends RuntimeException {
    public FfmpegExecutionException(String inputFile, int exitCode) {
        super("FFmpeg conversion for musicFile [" + inputFile + "] failed with exit code " + exitCode);
    }
}
