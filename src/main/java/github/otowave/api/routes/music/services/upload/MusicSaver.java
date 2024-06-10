package github.otowave.api.routes.music.services.upload;

import github.otowave.api.routes.common.entities.MetaEntity;
import github.otowave.api.routes.common.entities.ProfileEntity;
import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.music.entities.MusicProfileEntity;
import github.otowave.api.routes.music.repositories.MusicMetaRepo;
import github.otowave.api.routes.music.repositories.MusicProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static github.otowave.api.configuration.StaticContentDirs.MUSIC_DIR;

@Service
public class MusicSaver {
    @Autowired
    MusicProfileRepo musicProfileRepo;
    @Autowired
    MusicMetaRepo musicMetaRepo;

    @Transactional
    public Mono<Integer> save(MusicProfileEntity profileEntity, String tale, Mono<FilePart> musicFile) {
        return saveProfileEntity(profileEntity)
                .flatMap(newImageID -> createMetaEntity(newImageID, tale))
                .flatMap(this::saveMetaEntity)
                .flatMap(newImageID -> saveFile(newImageID, musicFile));
    }

    private Mono<Integer> saveProfileEntity(MusicProfileEntity profileEntity) {
        return musicProfileRepo.save(profileEntity).map(ProfileEntity::getItemID);
    }

    private Mono<MusicMetaEntity> createMetaEntity(int imageID, String tale) {
        return Mono.just(new MusicMetaEntity(imageID, tale));
    }

    private Mono<Integer> saveMetaEntity(MusicMetaEntity metaEntity) {
        return musicMetaRepo.save(metaEntity).map(MetaEntity::getItemID);
    }

    private Mono<Integer> saveFile(int musicID, Mono<FilePart> musicFile) {
        return musicFile.flatMap(file -> {
            String id = String.valueOf(musicID);
            Path path = Paths.get(MUSIC_DIR.getDir(), id);
            return createDir(path)
                    .then(file.transferTo(path.resolve(id)))
                    .onErrorResume(e -> Mono.error(new RuntimeException("File upload failed", e)));
        }).thenReturn(musicID);
    }

    private Mono<Path> createDir(Path path) {
        return Mono.fromCallable(() -> Files.createDirectories(path))
                .onErrorResume(e -> Mono.error(new RuntimeException("Cannot create dir " + path, e)));
    }
}
