package github.otowave.api.routes.songlists.repositories.playlists;

import github.otowave.api.routes.songlists.entities.playlists.PlaylistsFillingEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlaylistsFillingRepo extends ReactiveCrudRepository<PlaylistsFillingEntity, Integer> {
}
