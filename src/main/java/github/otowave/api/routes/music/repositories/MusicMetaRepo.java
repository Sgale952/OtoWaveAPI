package github.otowave.api.routes.music.repositories;

import github.otowave.api.routes.music.entities.MusicMetaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface MusicMetaRepo extends ReactiveCrudRepository<MusicMetaEntity, Integer> {
    @Query("SELECT * FROM music.meta ORDER BY RANDOM() LIMIT 50")
    Flux<MusicMetaEntity> findRandomPage();

    @Query("SELECT * FROM music.meta ORDER BY created DESC LIMIT :limit OFFSET :offset")
    Flux<MusicMetaEntity> findAllWithPages(@Param("limit") int limit, @Param("offset") int offset);

    @Query("SELECT * FROM music.meta WHERE EXTRACT(YEAR FROM created) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM created) = EXTRACT(MONTH FROM CURRENT_DATE) ORDER BY listens DESC LIMIT :limit OFFSET :offset")
    Flux<MusicMetaEntity> findAllPerMonthWithPages(@Param("limit") int limit, @Param("offset") int offset);
}
