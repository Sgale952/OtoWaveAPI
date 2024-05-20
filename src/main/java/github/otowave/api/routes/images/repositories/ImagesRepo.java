package github.otowave.api.routes.images.repositories;

import github.otowave.api.routes.images.entities.ImagesEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ImagesRepo extends ReactiveCrudRepository<ImagesEntity, Integer> {
}