package github.otowave.api.routes.playlists.repositories;

import github.otowave.api.routes.playlists.entities.PlaylistsMetaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlaylistsMetaRepo extends ReactiveCrudRepository<PlaylistsMetaEntity, Integer> {
}
