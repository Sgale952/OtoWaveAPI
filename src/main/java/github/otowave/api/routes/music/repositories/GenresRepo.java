package github.otowave.api.routes.music.repositories;

import github.otowave.api.routes.music.entities.GenresEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenresRepo extends ReactiveCrudRepository<GenresEntity, String> {
}
