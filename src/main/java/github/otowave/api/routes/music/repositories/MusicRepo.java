package github.otowave.api.routes.music.repositories;

import github.otowave.api.routes.music.entities.MusicEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface MusicRepo extends ReactiveCrudRepository<MusicEntity, Integer> {
    @Query("SELECT * FROM music.music WHERE music_id = :musicID")
    Mono<MusicEntity> findMusicByMusicID(@Param("musicID") int musicID);
}