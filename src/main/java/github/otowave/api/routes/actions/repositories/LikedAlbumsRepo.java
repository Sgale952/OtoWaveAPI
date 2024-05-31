package github.otowave.api.routes.actions.repositories;

import github.otowave.api.routes.actions.entities.LikedAlbumsEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface LikedAlbumsRepo extends ReactiveCrudRepository<LikedAlbumsEntity, Integer> {
    Flux<LikedAlbumsEntity> findAllByUserID(Publisher<Integer> userIDStream);
}
