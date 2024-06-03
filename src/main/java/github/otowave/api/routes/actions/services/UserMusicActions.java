package github.otowave.api.routes.actions.services;

import github.otowave.api.routes.actions.entities.LikedMusicEntity;
import github.otowave.api.routes.actions.entities.ListenedMusicEntity;
import github.otowave.api.routes.actions.repositories.LikedMusicRepo;
import github.otowave.api.routes.actions.repositories.ListenedMusicRepo;
import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.repositories.MusicMetaRepo;
import github.otowave.api.routes.music.repositories.MusicProfileRepo;
import github.otowave.api.routes.music.services.faces.MusicFaceMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserMusicActions {
    @Autowired
    MusicFaceMaker musicFaceMaker;
    @Autowired
    MusicProfileRepo musicProfileRepo;
    @Autowired
    MusicMetaRepo musicMetaRepo;
    @Autowired
    LikedMusicRepo likedMusicRepo;
    @Autowired
    ListenedMusicRepo listenedMusicRepo;


    public UserMusicActions() {
    }

    public Flux<MusicFaceModel> getCreatedFaces(int userID) {
        return musicFaceMaker.getFaceModelsFromProfile(musicProfileRepo.findByAuthorID(userID));
    }

    public Flux<MusicFaceModel> getLikedMusicFaces(int userID) {
        return musicFaceMaker.getFaceModelsFromActions(likedMusicRepo.findAllByUserID(Mono.just(userID)));
    }

    public Mono<Void> listenMusic(int userID, int musicID) {
        return listenedMusicRepo.save(new ListenedMusicEntity(userID, musicID))
                .then(increaseListens(musicID)).then();
    }

    public Mono<Void> likeMusic(int userID, int musicID) {
        return likedMusicRepo.save(new LikedMusicEntity(userID, musicID))
                .then(increaseLikes(musicID)).then();
    }

    public Mono<Void> discardMusic(int userID, int musicID) {
        return likedMusicRepo.deleteByUserIDAndItemID(userID, musicID)
                .then(decriesLikes(musicID)).then();
    }

    private Mono<MusicMetaEntity> increaseListens(int musicID) {
        return musicMetaRepo.increaseListens(musicID);
    }

    private Mono<MusicMetaEntity> increaseLikes(int musicID) {
        return musicMetaRepo.increaseLikes(musicID);
    }

    private Mono<MusicMetaEntity> decriesLikes(int musicID) {
        return musicMetaRepo.decriesLikes(musicID);
    }
}
