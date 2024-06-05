package github.otowave.api.routes.songlists.services.albums.faces;

import github.otowave.api.routes.common.entities.ActionsEntity;
import github.otowave.api.routes.common.services.FaceMaker;
import github.otowave.api.routes.songlists.entities.albums.AlbumsMetaEntity;
import github.otowave.api.routes.songlists.entities.albums.AlbumsProfileEntity;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class AlbumsFaceMaker extends FaceMaker<SonglistFaceModel, AlbumsMetaEntity, AlbumsProfileEntity> {
    @Autowired
    AlbumsProfileRepo albumsProfileRepo;

    public <T extends ActionsEntity> Flux<SonglistFaceModel> getFaceModelsFromActions(Flux<T> actionsEntity) {
        return actionsEntity
                .flatMap(entity -> albumsProfileRepo.findById(entity.getItemID())
                        .flatMap(this::makeFaceModel));
    }

    @Override
    protected Flux<SonglistFaceModel> getFaceModelsFromMeta(Flux<AlbumsMetaEntity> metaEntities) {
        return metaEntities
                .flatMap(entity -> albumsProfileRepo.findById(entity.getItemID())
                        .flatMap(this::makeFaceModel));
    }

    @Override
    protected Mono<SonglistFaceModel> makeFaceModel(AlbumsProfileEntity profileEntity) {
        int albumID = profileEntity.getItemID();
        String title = profileEntity.getTitle();
        int authorID = profileEntity.getCreatorID();
        int coverID = profileEntity.getCoverID();
        String genre = profileEntity.getGenre();
        boolean econtent = profileEntity.getEcontent();

        return getUsername(authorID)
                .map(authorName -> new SonglistFaceModel(albumID, authorID, coverID, authorName, title, genre, econtent));
    }

    protected Flux<SonglistFaceModel> filterByGenre(Flux<SonglistFaceModel> faces, String genre) {
        return faces.filter(face -> Objects.equals(face.getGenre(), genre));
    }
}
