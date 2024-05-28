package github.otowave.api.routes.albums.repositories;

import github.otowave.api.routes.albums.entities.AlbumsProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AlbumsProfileRepo extends ReactiveCrudRepository<AlbumsProfileEntity, Integer> {
}
