package github.otowave.api.routes.songlists.repositories.playlists;

import github.otowave.api.routes.songlists.entities.playlists.PlaylistsMetaEntity;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsProfileEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlaylistsMetaRepo extends ReactiveCrudRepository<PlaylistsMetaEntity, Integer> {
    Mono<PlaylistsMetaEntity> findAllByItemID(int itemID);

    @Query("SELECT * FROM playlists.meta ORDER BY RANDOM() LIMIT 50")
    Flux<PlaylistsMetaEntity> findRandomPage();
}
