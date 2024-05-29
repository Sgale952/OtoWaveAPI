package github.otowave.api.routes.songlists.repositories.playlists;

import github.otowave.api.routes.songlists.entities.playlists.PlaylistsProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PlaylistsProfileRepo extends ReactiveCrudRepository<PlaylistsProfileEntity, Integer> {
    Flux<PlaylistsProfileEntity> findByCreatorID(int creatorID);
}
