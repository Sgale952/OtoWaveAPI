package github.otowave.api.routes.music.repositories;

import github.otowave.api.routes.music.entities.GenresEntity;
import org.springframework.data.repository.CrudRepository;

public interface GenresRepo extends CrudRepository<GenresEntity, String> {}
