package github.otowave.api.routes.actions.repositories;

import github.otowave.api.routes.actions.entities.LikedPlaylistsEntity;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface LikedPlaylistsRepo extends ReactiveCrudRepository<LikedPlaylistsEntity, Integer> {
    Flux<LikedPlaylistsEntity> findAllByUserID(Publisher<Integer> userIDStream);
}
