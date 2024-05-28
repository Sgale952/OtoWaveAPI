package github.otowave.api.routes.music.services.faces;

import github.otowave.api.routes.music.entities.MusicProfileEntity;
import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.repositories.MusicProfileRepo;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class MusicFacesMaker {
    @Autowired
    private MusicProfileRepo musicProfileRepo;
    @Autowired
    private UsersProfileRepo usersProfileRepo;

    protected Flux<MusicFaceModel> getFaceModels(Flux<MusicMetaEntity> musicMetaEntities) {
        return musicMetaEntities
                .flatMap(entity -> musicProfileRepo.findById(entity.getMusicID())
                        .flatMap(this::makeFaceModel));
    }

    protected Flux<MusicFaceModel> filterByGenre(Flux<MusicFaceModel> musicFaces, String genre) {
        return musicFaces.filter(face -> Objects.equals(face.genre(), genre));
    }

    private Mono<MusicFaceModel> makeFaceModel(MusicProfileEntity musicProfileEntity) {
        int musicID = musicProfileEntity.getMusicID();
        String title = musicProfileEntity.getTitle();
        int authorID = musicProfileEntity.getAuthorID();
        int coverID = musicProfileEntity.getCoverID();
        String genre = musicProfileEntity.getGenre();
        boolean econtent = musicProfileEntity.getEcontent();

        return getUsername(authorID)
                .map(authorName -> new MusicFaceModel(musicID, authorID, coverID, title, authorName, genre, econtent));
    }

    private Mono<String> getUsername(int userID) {
        return usersProfileRepo.findById(userID).map(UsersProfileEntity::getNickname);
    }
}
