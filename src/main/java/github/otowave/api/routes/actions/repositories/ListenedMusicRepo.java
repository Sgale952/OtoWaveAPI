package github.otowave.api.routes.actions.repositories;

import github.otowave.api.routes.actions.entities.ListenedMusicEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ListenedMusicRepo extends ReactiveCrudRepository<ListenedMusicEntity, Integer> {
    Flux<ListenedMusicEntity> findAllByUserID(Publisher<Integer> userIDStream);
}
