package github.otowave.api.routes.songlists.services.playlists;

import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsProfileEntity;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsProfileRepo;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PlaylistsFaceMaker {
    @Autowired
    PlaylistsProfileRepo playlistsProfileRepo;
    @Autowired
    private UsersProfileRepo usersProfileRepo;
    
/*    protected Flux<MusicFaceModel> getFaceModels(Flux<PlaylistsProfileEntity> playlistsProfileEntity) {
        
    }

    private Mono<PlaylistsFaceMaker> makeFaceModel(PlaylistsProfileEntity playlistsProfileEntity) {
        int musicID = playlistsProfileEntity.getPlaylistID();
        String title = playlistsProfileEntity.getTitle();
        int authorID = playlistsProfileEntity.getCreatorID();
        int coverID = playlistsProfileEntity.getCoverID();
        String genre = playlistsProfileEntity.getGenre();
        boolean econtent = playlistsProfileEntity.getEcontent();

        return getUsername(authorID)
                .map(authorName -> new MusicFaceModel(musicID, authorID, coverID, title, authorName, genre, econtent));
    }

    private Mono<String> getUsername(int userID) {
        return usersProfileRepo.findById(userID).map(UsersProfileEntity::getUsername);
    }*/
}
