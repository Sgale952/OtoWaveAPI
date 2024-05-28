package github.otowave.api.routes.music.repositories;

import github.otowave.api.routes.music.entities.MusicProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MusicProfileRepo extends ReactiveCrudRepository<MusicProfileEntity, Integer> {
}