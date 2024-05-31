package github.otowave.api.routes.music.services;

import github.otowave.api.routes.images.services.upload.apply.ImageApplier;
import github.otowave.api.routes.music.repositories.MusicProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MusicDeleter {
    @Autowired
    MusicProfileRepo musicProfileRepo;
    @Autowired
    ImageApplier imageApplier;

    public MusicDeleter() {
    }

    public Mono<Integer> delete(int musicID) {
        return imageApplier.resetImage("music", musicID)
                .then(musicProfileRepo.findById(musicID))
                .flatMap(entity -> musicProfileRepo.delete(entity))
                .thenReturn(musicID);
    }


}
