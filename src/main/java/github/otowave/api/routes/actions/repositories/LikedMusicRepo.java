package github.otowave.api.routes.actions.repositories;

import github.otowave.api.routes.actions.entities.LikedMusicEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LikedMusicRepo extends ReactiveCrudRepository<LikedMusicEntity, Integer> {
}
