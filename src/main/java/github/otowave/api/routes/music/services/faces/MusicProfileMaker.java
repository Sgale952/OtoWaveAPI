package github.otowave.api.routes.music.services.faces;

import github.otowave.api.routes.common.services.ProfileMaker;
import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.models.MusicProfileModel;
import github.otowave.api.routes.music.services.faces.MusicFaceMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class MusicProfileMaker extends ProfileMaker<MusicProfileModel, MusicMetaEntity, MusicFaceModel> {
    @Autowired
    MusicFaceMaker musicFaceMaker;

    public MusicProfileMaker() {
    }

    public Mono<MusicProfileModel> getProfile(Mono<MusicMetaEntity> musicMetaEntity) {
        Mono<MusicFaceModel> faceModel = musicFaceMaker.getFaceModelsFromMeta(musicMetaEntity.flux()).singleOrEmpty();
        return musicMetaEntity.flatMap(entity -> makeProfile(faceModel, entity));
    }

    protected Mono<MusicProfileModel> makeProfile(Mono<MusicFaceModel> faceModel, MusicMetaEntity musicMetaEntity) {
        return faceModel.flatMap(face -> {
            String tale = musicMetaEntity.getTale();
            int likes = musicMetaEntity.getLikes();
            int listens = musicMetaEntity.getListens();
            LocalDateTime created = musicMetaEntity.getCreated();
            return Mono.just(new MusicProfileModel(face, tale, likes, listens, created));
        });
    }
}