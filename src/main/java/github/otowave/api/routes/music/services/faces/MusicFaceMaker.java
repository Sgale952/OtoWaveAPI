package github.otowave.api.routes.music.services.faces;

import github.otowave.api.routes.common.entities.ActionsEntity;
import github.otowave.api.routes.common.entities.FillingEntity;
import github.otowave.api.routes.common.services.FaceMaker;
import github.otowave.api.routes.music.entities.MusicProfileEntity;
import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.repositories.MusicProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class MusicFaceMaker extends FaceMaker<MusicFaceModel, MusicMetaEntity, MusicProfileEntity> {
    @Autowired
    private MusicProfileRepo musicProfileRepo;

    public MusicFaceMaker() {
    }

    public <T extends FillingEntity> Flux<MusicFaceModel> getFaceModelsFromFilling(Flux<T> fillingEntity) {
        return fillingEntity
                .flatMap(entity -> musicProfileRepo.findById(entity.getMusicID())
                        .flatMap(this::makeFaceModel));
    }

    public <T extends ActionsEntity> Flux<MusicFaceModel> getFaceModelsFromActions(Flux<T> actionsEntity) {
        return actionsEntity
                .flatMap(entity -> musicProfileRepo.findById(entity.getItemID())
                        .flatMap(this::makeFaceModel));
    }

    @Override
    public Flux<MusicFaceModel> getFaceModelsFromMeta(Flux<MusicMetaEntity> metaEntities) {
        return metaEntities
                .flatMap(entity -> musicProfileRepo.findById(entity.getItemID())
                        .flatMap(this::makeFaceModel));
    }

    @Override
    protected Mono<MusicFaceModel> makeFaceModel(MusicProfileEntity profileEntity) {
        int musicID = profileEntity.getItemID();
        String title = profileEntity.getTitle();
        int authorID = profileEntity.getAuthorID();
        int coverID = profileEntity.getCoverID();
        String genre = profileEntity.getGenre();
        boolean econtent = profileEntity.getEcontent();

        return getUsername(authorID)
                .map(authorName -> new MusicFaceModel(musicID, authorID, coverID, authorName, title, genre, econtent));
    }

    protected Flux<MusicFaceModel> filterByGenre(Flux<MusicFaceModel> faces, String genre) {
        return faces.filter(face -> Objects.equals(face.getGenre(), genre));
    }
}
