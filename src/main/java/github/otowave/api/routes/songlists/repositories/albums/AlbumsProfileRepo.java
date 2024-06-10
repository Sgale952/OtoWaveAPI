package github.otowave.api.routes.songlists.repositories.albums;

import github.otowave.api.routes.songlists.entities.albums.AlbumsProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AlbumsProfileRepo extends ReactiveCrudRepository<AlbumsProfileEntity, Integer> {
    Flux<AlbumsProfileEntity> findByCreatorID(int creatorID);
}
