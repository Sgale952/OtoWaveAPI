package github.otowave.api.routes.music.repositories;

import github.otowave.api.routes.music.entities.MusicProfileEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MusicProfileRepo extends ReactiveCrudRepository<MusicProfileEntity, Integer> {
    Flux<MusicProfileEntity> findByAuthorID(int authorID);

    @Query("SELECT * FROM music.profile WHERE genre = :genre ORDER BY RANDOM() LIMIT 50")
    Flux<MusicProfileEntity> findPageByGenre(String genre);
}