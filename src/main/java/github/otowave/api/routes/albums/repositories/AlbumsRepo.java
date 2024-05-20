package github.otowave.api.routes.albums.repositories;

import github.otowave.api.routes.albums.entities.AlbumsEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AlbumsRepo extends ReactiveCrudRepository<AlbumsEntity, Integer> {
}
