package github.otowave.api.routes.playlists.repositories;

import github.otowave.api.routes.playlists.entities.PlaylistsProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlaylistsProfileRepo extends ReactiveCrudRepository<PlaylistsProfileEntity, Integer> {
}
