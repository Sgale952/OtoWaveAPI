package github.otowave.api.routes.songlists.repositories.albums;

import github.otowave.api.routes.songlists.entities.albums.AlbumsFillingEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface AlbumsFillingRepo extends ReactiveCrudRepository<AlbumsFillingEntity, Integer> {
}
