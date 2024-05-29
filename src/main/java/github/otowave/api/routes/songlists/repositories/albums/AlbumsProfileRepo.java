package github.otowave.api.routes.songlists.repositories.albums;

import github.otowave.api.routes.songlists.entities.albums.AlbumsProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface AlbumsProfileRepo extends ReactiveCrudRepository<AlbumsProfileEntity, Integer> {
    Flux<AlbumsProfileEntity> findByCreatorID(int creatorID);
}
