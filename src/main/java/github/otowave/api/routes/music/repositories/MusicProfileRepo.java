package github.otowave.api.routes.music.repositories;

import github.otowave.api.routes.music.entities.MusicProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MusicProfileRepo extends ReactiveCrudRepository<MusicProfileEntity, Integer> {
    Flux<MusicProfileEntity> findByAuthorID(int authorID);
}