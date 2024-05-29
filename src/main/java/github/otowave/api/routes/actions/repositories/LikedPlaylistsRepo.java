package github.otowave.api.routes.actions.repositories;

import github.otowave.api.routes.actions.entities.LikedPlaylistsEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LikedPlaylistsRepo extends ReactiveCrudRepository<LikedPlaylistsEntity, Integer> {
}
