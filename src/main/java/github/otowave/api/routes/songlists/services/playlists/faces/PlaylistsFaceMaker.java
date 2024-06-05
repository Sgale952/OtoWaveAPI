package github.otowave.api.routes.songlists.services.playlists.faces;

import github.otowave.api.routes.common.entities.ActionsEntity;
import github.otowave.api.routes.common.services.FaceMaker;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsMetaEntity;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsProfileEntity;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class PlaylistsFaceMaker extends FaceMaker<SonglistFaceModel, PlaylistsMetaEntity, PlaylistsProfileEntity> {
    @Autowired
    PlaylistsProfileRepo playlistsProfileRepo;

    public <T extends ActionsEntity> Flux<SonglistFaceModel> getFaceModelsFromActions(Flux<T> actionsEntity) {
        return actionsEntity
                .flatMap(entity -> playlistsProfileRepo.findById(entity.getItemID())
                        .flatMap(this::makeFaceModel));
    }

    @Override
    public Flux<SonglistFaceModel> getFaceModelsFromMeta(Flux<PlaylistsMetaEntity> metaEntities) {
        return metaEntities
                .flatMap(entity -> playlistsProfileRepo.findById(entity.getItemID())
                        .flatMap(this::makeFaceModel));
    }

    @Override
    protected Mono<SonglistFaceModel> makeFaceModel(PlaylistsProfileEntity profileEntity) {
        int playlistID = profileEntity.getItemID();
        String title = profileEntity.getTitle();
        int authorID = profileEntity.getCreatorID();
        int coverID = profileEntity.getCoverID();
        String genre = profileEntity.getGenre();
        boolean econtent = profileEntity.getEcontent();

        return getUsername(authorID)
                .map(authorName -> new SonglistFaceModel(playlistID, authorID, coverID, authorName, title, genre, econtent));
    }

    protected Flux<SonglistFaceModel> filterByGenre(Flux<SonglistFaceModel> faces, String genre) {
        return faces.filter(face -> Objects.equals(face.getGenre(), genre));
    }
}
