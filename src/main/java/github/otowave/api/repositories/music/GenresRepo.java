package github.otowave.api.repositories.music;

import github.otowave.api.entities.music.GenresEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GenresRepo extends CrudRepository<GenresEntity, String> {}
