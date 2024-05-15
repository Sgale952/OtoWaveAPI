package github.otowave.api.routes.albums.repositories;

import github.otowave.api.routes.albums.entities.AlbumsMetaEntity;
import org.springframework.data.repository.CrudRepository;

public interface AlbumsMetaRepo extends CrudRepository<AlbumsMetaEntity, Integer> {
}
