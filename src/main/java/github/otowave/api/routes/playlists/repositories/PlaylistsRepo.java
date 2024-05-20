package github.otowave.api.routes.playlists.repositories;

import github.otowave.api.routes.playlists.entities.PlaylistsEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlaylistsRepo extends ReactiveCrudRepository<PlaylistsEntity, Integer> {
}
