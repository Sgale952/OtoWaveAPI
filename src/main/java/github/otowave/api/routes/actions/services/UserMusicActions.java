package github.otowave.api.routes.actions.services;

import github.otowave.api.routes.actions.repositories.LikedMusicRepo;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.repositories.MusicProfileRepo;
import github.otowave.api.routes.music.services.faces.MusicFaceMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserMusicActions {
    @Autowired
    MusicFaceMaker musicFaceMaker;
    @Autowired
    MusicProfileRepo musicProfileRepo;
    @Autowired
    LikedMusicRepo likedMusicRepo;

    public UserMusicActions() {
    }

    public Flux<MusicFaceModel> getCreatedMusicFaces(int userID) {
        return musicFaceMaker.getFaceModelsFromProfile(musicProfileRepo.findByAuthorID(userID));
    }

    public Flux<MusicFaceModel> getLikedMusicFaces(int userID) {
        return musicFaceMaker.getFaceModelsFromActions(likedMusicRepo.findAllByUserID(Mono.just(userID)));
    }
}
