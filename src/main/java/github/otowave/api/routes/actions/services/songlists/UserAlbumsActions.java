package github.otowave.api.routes.actions.services.songlists;

import github.otowave.api.routes.actions.entities.LikedAlbumsEntity;
import github.otowave.api.routes.actions.repositories.LikedAlbumsRepo;
import github.otowave.api.routes.songlists.entities.albums.AlbumsMetaEntity;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsMetaRepo;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsProfileRepo;
import github.otowave.api.routes.songlists.services.albums.faces.AlbumsFaceMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class UserAlbumsActions {
    @Autowired
    AlbumsFaceMaker albumsFaceMaker;
    @Autowired
    AlbumsProfileRepo albumsProfileRepo;
    @Autowired
    AlbumsMetaRepo albumsMetaRepo;
    @Autowired
    LikedAlbumsRepo likedAlbumsRepo;

    public UserAlbumsActions() {
    }

    public Flux<SonglistFaceModel> getCreatedFaces(int userID) {
        return albumsFaceMaker.getFaceModelsFromProfile(albumsProfileRepo.findByCreatorID(userID));
    }

    public Flux<SonglistFaceModel> getLikedFaces(int userID) {
        return albumsFaceMaker.getFaceModelsFromActions(likedAlbumsRepo.findAllByUserID(Mono.just(userID)));
    }

    public Mono<Void> like(int userID, int albumID) {
        return likedAlbumsRepo.save(new LikedAlbumsEntity(userID, albumID))
                .then(increaseLikes(albumID)).then();
    }

    public Mono<Void> discard(int userID, int albumID) {
        return likedAlbumsRepo.deleteByUserIDAndItemID(userID, albumID)
                .then(decriesLikes(albumID)).then();
    }

    private Mono<AlbumsMetaEntity> increaseLikes(int albumID) {
        return albumsMetaRepo.increaseLikes(albumID);
    }

    private Mono<AlbumsMetaEntity> decriesLikes(int albumID) {
        return albumsMetaRepo.decriesLikes(albumID);
    }
}
