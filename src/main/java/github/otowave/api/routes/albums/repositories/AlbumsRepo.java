package github.otowave.api.routes.albums.repositories;

import github.otowave.api.routes.albums.entities.AlbumsEntity;
import org.springframework.data.repository.CrudRepository;

public interface AlbumsRepo extends CrudRepository<AlbumsEntity, Integer> {
}
