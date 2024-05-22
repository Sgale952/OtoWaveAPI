package github.otowave.api.routes.music.repositories;

import github.otowave.api.routes.music.entities.GenresEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface GenresRepo extends ReactiveCrudRepository<GenresEntity, String> {
}
