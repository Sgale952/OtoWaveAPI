package github.otowave.api.routes.music.repositories;

import github.otowave.api.routes.music.entities.MusicEntity;
import org.springframework.data.repository.CrudRepository;

public interface MusicRepo extends CrudRepository<MusicEntity, Integer> {}