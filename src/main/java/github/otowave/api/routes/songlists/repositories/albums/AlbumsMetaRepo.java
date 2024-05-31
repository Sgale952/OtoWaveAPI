package github.otowave.api.routes.songlists.repositories.albums;

import github.otowave.api.routes.songlists.entities.albums.AlbumsMetaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AlbumsMetaRepo extends ReactiveCrudRepository<AlbumsMetaEntity, Integer> {
    Mono<AlbumsMetaEntity> findByItemID(int itemID);
}
