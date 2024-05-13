package github.otowave.api.repositories.playlists;

import github.otowave.api.entities.playlists.PlaylistsMetaEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlaylistsMetaRepo extends CrudRepository<PlaylistsMetaEntity, Integer> {
}
