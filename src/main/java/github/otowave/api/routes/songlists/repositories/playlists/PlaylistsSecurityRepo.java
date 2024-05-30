package github.otowave.api.routes.songlists.repositories.playlists;

import github.otowave.api.routes.songlists.entities.playlists.PlaylistsSecurityEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlaylistsSecurityRepo extends ReactiveCrudRepository<PlaylistsSecurityEntity, Integer> {
}
