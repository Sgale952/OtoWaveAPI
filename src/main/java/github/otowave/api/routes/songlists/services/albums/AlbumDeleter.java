package github.otowave.api.routes.songlists.services.albums;

import github.otowave.api.routes.images.services.upload.apply.ImageApplier;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AlbumDeleter {
    @Autowired
    AlbumsProfileRepo albumsProfileRepo;
    @Autowired
    ImageApplier imageApplier;

    public AlbumDeleter() {
    }

    public Mono<Integer> delete(int albumID) {
        return imageApplier.resetImage("albums", albumID)
                .then(albumsProfileRepo.deleteById(albumID))
                .thenReturn(albumID);
    }
}
