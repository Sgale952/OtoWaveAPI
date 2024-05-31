package github.otowave.api.routes.songlists.repositories.playlists;

import github.otowave.api.routes.songlists.entities.playlists.PlaylistsMetaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PlaylistsMetaRepo extends ReactiveCrudRepository<PlaylistsMetaEntity, Integer> {
    Mono<PlaylistsMetaEntity> findAllByItemID(int itemID);
}
