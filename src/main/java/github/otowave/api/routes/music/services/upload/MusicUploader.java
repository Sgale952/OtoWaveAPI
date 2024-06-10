package github.otowave.api.routes.music.services.upload;

import github.otowave.api.routes.music.entities.MusicProfileEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MusicUploader {
    @Autowired
    MusicSaver musicSaver;
    @Autowired
    MusicConverter musicConverter;

    public Mono<Integer> upload(MusicProfileEntity profileEntity, String tale, Mono<FilePart> musicFile) {
        return musicSaver.save(profileEntity, tale, musicFile).flatMap(id -> musicConverter.prepareToHls(id).thenReturn(id));
    }
}
