package github.otowave.api.routes.songlists.repositories.playlists;

import github.otowave.api.routes.songlists.entities.playlists.PlaylistsProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlaylistsProfileRepo extends ReactiveCrudRepository<PlaylistsProfileEntity, Integer> {
}
