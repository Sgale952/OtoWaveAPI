package github.otowave.api.routes.images.repositories;

import github.otowave.api.routes.images.entities.ImagesEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagesRepo extends ReactiveCrudRepository<ImagesEntity, Integer> {
}