package github.otowave.api.routes.actions.repositories;

import github.otowave.api.routes.actions.entities.LikedAuthorsEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LikedAuthorsRepo extends ReactiveCrudRepository<LikedAuthorsEntity, Integer> {
    Flux<LikedAuthorsEntity> findAllByUserID(Publisher<Integer> userIDStream);

    Flux<LikedAuthorsEntity> findAllByItemID(Publisher<Integer> userIDStream);

    @Query("DELETE FROM actions.liked_authors WHERE user_id = :userID AND item_id = :itemID")
    Mono<LikedAuthorsEntity> deleteByUserIDAndItemID(int userID, int itemID);
}
