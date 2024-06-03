package github.otowave.api.routes.actions.repositories;

import github.otowave.api.routes.actions.entities.ListenedMusicEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ListenedMusicRepo extends ReactiveCrudRepository<ListenedMusicEntity, Integer> {
    Flux<ListenedMusicEntity> findAllByUserID(Publisher<Integer> userIDStream);
}
