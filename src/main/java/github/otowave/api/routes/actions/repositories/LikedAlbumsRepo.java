package github.otowave.api.routes.actions.repositories;

import github.otowave.api.routes.actions.entities.LikedAlbumsEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LikedAlbumsRepo extends ReactiveCrudRepository<LikedAlbumsEntity, Integer> {
    Flux<LikedAlbumsEntity> findAllByUserID(Publisher<Integer> userIDStream);

    @Query("DELETE FROM actions.liked_albums WHERE user_id = :userID AND item_id = :itemID")
    Mono<LikedAlbumsEntity> deleteByUserIDAndItemID(int userID, int itemID);
}
