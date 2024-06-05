package github.otowave.api.routes.actions.services.songlists;

import github.otowave.api.routes.actions.entities.LikedPlaylistsEntity;
import github.otowave.api.routes.actions.repositories.LikedPlaylistsRepo;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsMetaEntity;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsMetaRepo;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsProfileRepo;
import github.otowave.api.routes.songlists.services.playlists.faces.PlaylistsFaceMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserPlaylistsActions {
    @Autowired
    PlaylistsFaceMaker playlistsFaceMaker;
    @Autowired
    PlaylistsProfileRepo playlistsProfileRepo;
    @Autowired
    PlaylistsMetaRepo playlistsMetaRepo;
    @Autowired
    LikedPlaylistsRepo likedPlaylistsRepo;

    public Flux<SonglistFaceModel> getCreated(int userID) {
        return playlistsFaceMaker.getFaceModelsFromProfile(playlistsProfileRepo.findByCreatorID(userID));
    }

    public Flux<SonglistFaceModel> getLiked(int userID) {
        return playlistsFaceMaker.getFaceModelsFromActions(likedPlaylistsRepo.findAllByUserID(Mono.just(userID)));
    }

    public Mono<Void> like(int userID, int playlistID) {
        return likedPlaylistsRepo.save(new LikedPlaylistsEntity(userID, playlistID))
                .then(increaseLikes(playlistID)).then();
    }

    public Mono<Void> discard(int userID, int playlistID) {
        return likedPlaylistsRepo.deleteByUserIDAndItemID(userID, playlistID)
                .then(decriesLikes(playlistID)).then();
    }

    private Mono<PlaylistsMetaEntity> increaseLikes(int playlistID) {
        return playlistsMetaRepo.increaseLikes(playlistID);
    }

    private Mono<PlaylistsMetaEntity> decriesLikes(int playlistID) {
        return playlistsMetaRepo.decriesLikes(playlistID);
    }
}
