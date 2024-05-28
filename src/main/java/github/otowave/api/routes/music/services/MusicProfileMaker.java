package github.otowave.api.routes.music.services;

import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.models.MusicProfileModel;
import github.otowave.api.routes.music.services.faces.MusicFaceMaker;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class MusicProfileMaker extends MusicFaceMaker {
    public MusicProfileMaker() {
    }

    public Mono<MusicProfileModel> getMusicProfile(Mono<MusicMetaEntity> musicMetaEntity) {
        Mono<MusicFaceModel> faceModel = getFaceModels(musicMetaEntity.flux()).singleOrEmpty();
        return musicMetaEntity.flatMap(entity -> makeMusicProfile(faceModel, entity));
    }

    private Mono<MusicProfileModel> makeMusicProfile(Mono<MusicFaceModel> faceModel, MusicMetaEntity musicMetaEntity) {
        return faceModel.flatMap(face -> {
            String tale = musicMetaEntity.getTale();
            int likes = musicMetaEntity.getLikes();
            int listens = musicMetaEntity.getListens();
            LocalDateTime uploaded = musicMetaEntity.getCreated();
            return Mono.just(new MusicProfileModel(face, tale, likes, listens, uploaded));
        });
    }
}