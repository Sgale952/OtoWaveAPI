package github.otowave.api.routes.songlists.repositories.albums;

import github.otowave.api.routes.songlists.entities.albums.AlbumsProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AlbumsProfileRepo extends ReactiveCrudRepository<AlbumsProfileEntity, Integer> {
}
