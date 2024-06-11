package github.otowave.api.routes.songlists.repositories.playlists;

import github.otowave.api.routes.songlists.entities.playlists.PlaylistsProfileEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PlaylistsProfileRepo extends ReactiveCrudRepository<PlaylistsProfileEntity, Integer> {
    Flux<PlaylistsProfileEntity> findByCreatorID(int creatorID);

    @Query("UPDATE playlists.profile SET title = :newName WHERE item_id = :itemID")
    Mono<PlaylistsProfileEntity> changeName(int itemID, String newName);
}
