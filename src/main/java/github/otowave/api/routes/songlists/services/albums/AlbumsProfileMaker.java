package github.otowave.api.routes.songlists.services.albums;

import github.otowave.api.routes.common.models.ProfileModel;
import github.otowave.api.routes.common.services.ProfileMaker;
import github.otowave.api.routes.songlists.entities.albums.AlbumsMetaEntity;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class AlbumsProfileMaker extends ProfileMaker<ProfileModel, AlbumsMetaEntity, SonglistFaceModel> {
    @Autowired
    AlbumsFaceMaker albumsFaceMaker;

    public AlbumsProfileMaker() {
    }

    @Override
    public Mono<ProfileModel> getProfile(Mono<AlbumsMetaEntity> metaEntity) {
        Mono<SonglistFaceModel> faceModel = albumsFaceMaker.getFaceModelsFromMeta(metaEntity.flux()).singleOrEmpty();
        return metaEntity.flatMap(entity -> makeProfile(faceModel, entity));
    }

    @Override
    protected Mono<ProfileModel> makeProfile(Mono<SonglistFaceModel> faceModel, AlbumsMetaEntity metaEntity) {
        return faceModel.flatMap(face -> {
            String tale = metaEntity.getTale();
            int likes = metaEntity.getLikes();
            LocalDateTime created = metaEntity.getCreated();
            return Mono.just(new ProfileModel(face, tale, likes, created));
        });
    }
}
