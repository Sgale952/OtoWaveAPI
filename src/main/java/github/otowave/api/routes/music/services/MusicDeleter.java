package github.otowave.api.routes.music.services;

import github.otowave.api.routes.images.services.upload.apply.ImageApplier;
import github.otowave.api.routes.music.repositories.MusicProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

import static github.otowave.api.configuration.StaticContentDirs.MUSIC_DIR;

@Service
public class MusicDeleter {
    @Autowired
    MusicProfileRepo musicProfileRepo;
    @Autowired
    ImageApplier imageApplier;

    public Mono<Integer> delete(int musicID) {
        return imageApplier.resetImage("music", musicID)
                .then(musicProfileRepo.findById(musicID))
                .flatMap(entity -> musicProfileRepo.delete(entity))
                .then(Mono.fromRunnable(() -> deleteSongDir(musicID)))
                .thenReturn(musicID);
    }

    private void deleteSongDir(int musicID) {
        try {
            Path dir = Path.of(MUSIC_DIR.getDir(), String.valueOf(musicID));
            deleteDir(dir);
        }
        catch (IOException e) {
            //TODO: handle exception
        }
    }

    private void deleteDir(Path dir) throws IOException {
        try (Stream<Path> musicFiles = Files.walk(dir)) {
            musicFiles
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }
}
