package github.otowave.api.routes.actions.repositories;

import github.otowave.api.routes.actions.entities.LikedMusicEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LikedMusicRepo extends ReactiveCrudRepository<LikedMusicEntity, Integer> {
    Flux<LikedMusicEntity> findAllByUserID(Publisher<Integer> userIDStream);

    @Query("DELETE FROM actions.liked_music WHERE user_id = :userID AND item_id = :itemID")
    Mono<LikedMusicEntity> deleteByUserIDAndItemID(int userID, int itemID);
}
