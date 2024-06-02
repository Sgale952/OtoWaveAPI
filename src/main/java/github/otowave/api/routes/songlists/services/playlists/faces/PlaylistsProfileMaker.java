package github.otowave.api.routes.songlists.services.playlists.faces;

import github.otowave.api.routes.common.services.ProfileMaker;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.services.faces.MusicFaceMaker;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsMetaEntity;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;
import github.otowave.api.routes.songlists.models.SonglistProfileModel;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsFillingRepo;
import github.otowave.api.routes.songlists.services.playlists.faces.PlaylistsFaceMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlaylistsProfileMaker extends ProfileMaker<SonglistProfileModel, PlaylistsMetaEntity, SonglistFaceModel> {
    @Autowired
    PlaylistsFaceMaker playlistsFaceMaker;
    @Autowired
    MusicFaceMaker musicFaceMaker;
    @Autowired
    PlaylistsFillingRepo playlistsFillingRepo;

    public PlaylistsProfileMaker() {
    }

    public Mono<SonglistProfileModel> getProfile(Mono<PlaylistsMetaEntity> metaEntity) {
        Mono<SonglistFaceModel> faceModel = playlistsFaceMaker.getFaceModelsFromMeta(metaEntity.flux()).singleOrEmpty();
        return metaEntity.flatMap(entity -> makeProfile(faceModel, entity));
    }

    protected Mono<SonglistProfileModel> makeProfile(Mono<SonglistFaceModel> faceModel, PlaylistsMetaEntity metaEntity) {
        return faceModel.flatMap(face -> {
            int playlistID = face.getItemID();
            String tale = metaEntity.getTale();
            int likes = metaEntity.getLikes();
            LocalDateTime created = metaEntity.getCreated();
            return getFilling(playlistID).map(musicFaceModels ->
                    new SonglistProfileModel(face, tale, likes, created, musicFaceModels));
        });
    }

    private Mono<List<MusicFaceModel>> getFilling(int playlistID) {
        return musicFaceMaker.getFaceModelsFromFilling(playlistsFillingRepo.findAllByItemID(Mono.just(playlistID))).collectList();
    }
}
