package github.otowave.api.routes.actions.repositories;

import github.otowave.api.routes.actions.entities.LikedAlbumsEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LikedAlbumsRepo extends ReactiveCrudRepository<LikedAlbumsEntity, Integer> {
}
