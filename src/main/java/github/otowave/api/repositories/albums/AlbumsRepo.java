package github.otowave.api.repositories.albums;

import github.otowave.api.entities.albums.AlbumsEntity;
import org.springframework.data.repository.CrudRepository;

public interface AlbumsRepo extends CrudRepository<AlbumsEntity, Integer> {
}
