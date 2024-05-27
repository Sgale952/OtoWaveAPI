package github.otowave.api.routes.music.services.faces;

import github.otowave.api.routes.music.entities.MusicEntity;
import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.repositories.MusicRepo;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class MusicFacesMaker {
    @Autowired
    private MusicRepo musicRepo;
    @Autowired
    private UsersProfileRepo usersProfileRepo;

    protected Flux<MusicFaceModel> getFaceModels(Flux<MusicMetaEntity> musicMetaEntities) {
        return musicMetaEntities
                .flatMap(entity -> musicRepo.findById(entity.getMusicID())
                        .flatMap(this::makeFaceModel));
    }

    protected Flux<MusicFaceModel> filterByGenre(Flux<MusicFaceModel> musicFaces, String genre) {
        return musicFaces.filter(face -> Objects.equals(face.genre(), genre));
    }

    private Mono<MusicFaceModel> makeFaceModel(MusicEntity musicEntity) {
        int musicID = musicEntity.getMusicID();
        String title = musicEntity.getTitle();
        int authorID = musicEntity.getAuthorID();
        int coverID = musicEntity.getCoverID();
        String genre = musicEntity.getGenre();
        boolean econtent = musicEntity.getEcontent();

        return getUsername(authorID)
                .map(authorName -> new MusicFaceModel(musicID, authorID, coverID, title, authorName, genre, econtent));
    }

    private Mono<String> getUsername(int userID) {
        return usersProfileRepo.findById(userID).map(UsersProfileEntity::getNickname);
    }
}
