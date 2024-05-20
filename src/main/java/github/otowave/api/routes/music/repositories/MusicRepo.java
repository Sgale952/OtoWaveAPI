package github.otowave.api.routes.music.repositories;

import github.otowave.api.routes.music.entities.MusicEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MusicRepo extends ReactiveCrudRepository<MusicEntity, Integer> {
}