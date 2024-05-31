package github.otowave.api.routes.actions.repositories;

import github.otowave.api.routes.actions.entities.LikedMusicEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface LikedMusicRepo extends ReactiveCrudRepository<LikedMusicEntity, Integer> {
    Flux<LikedMusicEntity> findAllByUserID(Publisher<Integer> userIDStream);
}
