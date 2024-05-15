package github.otowave.api.routes.playlists.repositories;

import github.otowave.api.routes.playlists.entities.PlaylistsEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistsRepo extends CrudRepository<PlaylistsEntity, Integer> {
}
