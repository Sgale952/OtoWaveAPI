package github.otowave.api.routes.songlists.repositories.playlists;

import github.otowave.api.routes.songlists.entities.playlists.PlaylistsFillingEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlaylistsFillingRepo extends ReactiveCrudRepository<PlaylistsFillingEntity, Integer> {
    Flux<PlaylistsFillingEntity> findAllByItemID(Publisher<Integer> itemIDStream);

    @Query("DELETE FROM playlists.filling WHERE item_id = :itemID AND music_id = :musicID")
    Mono<Void> deleteByItemIDAndMusicID(int itemID, int musicID);
}
