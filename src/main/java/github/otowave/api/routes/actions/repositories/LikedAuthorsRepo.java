package github.otowave.api.routes.actions.repositories;

import github.otowave.api.routes.actions.entities.LikedAuthorsEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface LikedAuthorsRepo extends ReactiveCrudRepository<LikedAuthorsEntity, Integer> {
    Flux<LikedAuthorsEntity> findAllByUserID(Publisher<Integer> userIDStream);

    Flux<LikedAuthorsEntity> findAllByItemID(Publisher<Integer> userIDStream);
}
