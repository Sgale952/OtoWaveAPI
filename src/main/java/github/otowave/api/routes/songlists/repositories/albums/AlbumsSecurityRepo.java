package github.otowave.api.routes.songlists.repositories.albums;

import github.otowave.api.routes.songlists.entities.albums.AlbumsSecurityEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AlbumsSecurityRepo extends ReactiveCrudRepository<AlbumsSecurityEntity, Integer> {
}
