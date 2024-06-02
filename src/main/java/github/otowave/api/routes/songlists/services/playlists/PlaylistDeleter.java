package github.otowave.api.routes.songlists.services.playlists;

import github.otowave.api.routes.images.services.upload.apply.ImageApplier;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PlaylistDeleter {
    @Autowired
    PlaylistsProfileRepo playlistsProfileRepo;
    @Autowired
    ImageApplier imageApplier;

    public PlaylistDeleter() {
    }

    public Mono<Integer> delete(int albumID) {
        return imageApplier.resetImage("playlists", albumID)
                .then(playlistsProfileRepo.deleteById(albumID))
                .thenReturn(albumID);
    }
}
