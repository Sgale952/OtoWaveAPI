package github.otowave.api.routes.songlists.repositories.playlists;

import github.otowave.api.routes.songlists.entities.playlists.PlaylistsMetaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlaylistsMetaRepo extends ReactiveCrudRepository<PlaylistsMetaEntity, Integer> {
}
