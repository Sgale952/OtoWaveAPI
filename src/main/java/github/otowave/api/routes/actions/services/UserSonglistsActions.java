package github.otowave.api.routes.actions.services;

import github.otowave.api.routes.actions.repositories.LikedAlbumsRepo;
import github.otowave.api.routes.actions.repositories.LikedPlaylistsRepo;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsProfileRepo;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsProfileRepo;
import github.otowave.api.routes.songlists.services.albums.AlbumsFaceMaker;
import github.otowave.api.routes.songlists.services.playlists.PlaylistsFaceMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UserSonglistsActions {
    @Autowired
    PlaylistsFaceMaker playlistsFaceMaker;
    @Autowired
    AlbumsFaceMaker albumsFaceMaker;
    @Autowired
    PlaylistsProfileRepo playlistsProfileRepo;
    @Autowired
    LikedPlaylistsRepo likedPlaylistsRepo;
    @Autowired
    AlbumsProfileRepo albumsProfileRepo;
    @Autowired
    LikedAlbumsRepo likedAlbumsRepo;

    public UserSonglistsActions() {
    }

    public Flux<SonglistFaceModel> getCreatedPlaylists(int userID) {
        return playlistsFaceMaker.getFaceModelsFromProfile(playlistsProfileRepo.findByCreatorID(userID));
    }

    public Flux<SonglistFaceModel> getLikedPlaylists(int userID) {
        return playlistsFaceMaker.getFaceModelsFromActions(likedPlaylistsRepo.findAllById(Flux.just(userID)));
    }

    public Flux<SonglistFaceModel> getCreatedAlbums(int userID) {
        return albumsFaceMaker.getFaceModelsFromProfile(albumsProfileRepo.findByCreatorID(userID));
    }

    public Flux<SonglistFaceModel> getLikedAlbums(int userID) {
        return albumsFaceMaker.getFaceModelsFromActions(likedAlbumsRepo.findAllById(Flux.just(userID)));
    }
}
