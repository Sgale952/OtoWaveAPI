package github.otowave.api.repositories.playlists;

import github.otowave.api.entities.playlists.PlaylistsEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistsRepo extends CrudRepository<PlaylistsEntity, Integer> {
}
