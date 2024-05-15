package github.otowave.api.routes.playlists.repositories;

import github.otowave.api.routes.playlists.entities.PlaylistsMetaEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistsMetaRepo extends CrudRepository<PlaylistsMetaEntity, Integer> {
}
