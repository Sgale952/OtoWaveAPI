package github.otowave.api.routes.albums.repositories;

import github.otowave.api.routes.albums.entities.AlbumsMetaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AlbumsMetaRepo extends ReactiveCrudRepository<AlbumsMetaEntity, Integer> {
}
