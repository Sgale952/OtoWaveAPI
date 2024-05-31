package github.otowave.api.routes.songlists.repositories.albums;

import github.otowave.api.routes.songlists.entities.albums.AlbumsFillingEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlbumsFillingRepo extends ReactiveCrudRepository<AlbumsFillingEntity, Integer> {
    Flux<AlbumsFillingEntity> findAllByItemID(Publisher<Integer> itemIDStream);

    @Query("DELETE FROM albums.filling WHERE item_id = :itemID AND music_id = :musicID")
    Mono<Void> deleteByItemIDAndMusicID(int itemID, int musicID);
}