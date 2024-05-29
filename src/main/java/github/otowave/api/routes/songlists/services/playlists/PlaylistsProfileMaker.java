package github.otowave.api.routes.songlists.services.playlists;

import github.otowave.api.routes.common.models.ProfileModel;
import github.otowave.api.routes.common.services.ProfileMaker;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsMetaEntity;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class PlaylistsProfileMaker extends ProfileMaker<ProfileModel, PlaylistsMetaEntity, SonglistFaceModel> {
    @Autowired
    PlaylistsFaceMaker playlistsFaceMaker;

    public PlaylistsProfileMaker() {
    }

    public Mono<ProfileModel> getProfile(Mono<PlaylistsMetaEntity> metaEntity) {
        Mono<SonglistFaceModel> faceModel = playlistsFaceMaker.getFaceModelsFromMeta(metaEntity.flux()).singleOrEmpty();
        return metaEntity.flatMap(entity -> makeProfile(faceModel, entity));
    }

    protected Mono<ProfileModel> makeProfile(Mono<SonglistFaceModel> faceModel, PlaylistsMetaEntity metaEntity) {
        return faceModel.flatMap(face -> {
            String tale = metaEntity.getTale();
            int likes = metaEntity.getLikes();
            LocalDateTime created = metaEntity.getCreated();
            return Mono.just(new ProfileModel(face, tale, likes, created));
        });
    }
}
