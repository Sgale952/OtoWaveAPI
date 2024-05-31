package github.otowave.api.routes.songlists.services.albums;

import github.otowave.api.routes.common.services.ProfileMaker;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.services.faces.MusicFaceMaker;
import github.otowave.api.routes.songlists.entities.albums.AlbumsMetaEntity;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;
import github.otowave.api.routes.songlists.models.SonglistProfileModel;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsFillingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlbumsProfileMaker extends ProfileMaker<SonglistProfileModel, AlbumsMetaEntity, SonglistFaceModel> {
    @Autowired
    AlbumsFaceMaker albumsFaceMaker;
    @Autowired
    MusicFaceMaker musicFaceMaker;
    @Autowired
    AlbumsFillingRepo albumsFillingRepo;

    public AlbumsProfileMaker() {
    }

    @Override
    public Mono<SonglistProfileModel> getProfile(Mono<AlbumsMetaEntity> metaEntity) {
        Mono<SonglistFaceModel> faceModel = albumsFaceMaker.getFaceModelsFromMeta(metaEntity.flux()).singleOrEmpty();
        return metaEntity.flatMap(entity -> makeProfile(faceModel, entity));
    }

    @Override
    protected Mono<SonglistProfileModel> makeProfile(Mono<SonglistFaceModel> faceModel, AlbumsMetaEntity metaEntity) {
        return faceModel.flatMap(face -> {
            int albumID = face.getItemID();
            String tale = metaEntity.getTale();
            int likes = metaEntity.getLikes();
            LocalDateTime created = metaEntity.getCreated();
            return getFilling(albumID).map(musicFaceModels ->
                    new SonglistProfileModel(face, tale, likes, created, musicFaceModels));
        });
    }

    private Mono<List<MusicFaceModel>> getFilling(int playlistID) {
        return musicFaceMaker.getFaceModelsFromFilling(albumsFillingRepo.findAllByItemID(Mono.just(playlistID))).collectList();
    }
}
