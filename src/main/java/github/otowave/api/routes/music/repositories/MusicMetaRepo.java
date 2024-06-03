package github.otowave.api.routes.music.repositories;

import github.otowave.api.routes.music.entities.MusicMetaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MusicMetaRepo extends ReactiveCrudRepository<MusicMetaEntity, Integer> {
    Mono<MusicMetaEntity> findByItemID(int itemID);

    @Query("UPDATE music.meta  SET listens = listens + 1 WHERE item_id = :musicID")
    Mono<MusicMetaEntity> increaseListens(int musicID);

    @Query("UPDATE music.meta  SET likes = Likes + 1 WHERE item_id = :musicID")
    Mono<MusicMetaEntity> increaseLikes(int musicID);

    @Query("UPDATE music.meta  SET likes = Likes - 1 WHERE item_id = :musicID")
    Mono<MusicMetaEntity> decriesLikes(int musicID);

    @Query("SELECT * FROM music.meta ORDER BY RANDOM() LIMIT 50")
    Flux<MusicMetaEntity> findRandomPage();

    @Query("SELECT * FROM music.meta ORDER BY created DESC LIMIT :limit OFFSET :offset")
    Flux<MusicMetaEntity> findAllWithPages(int limit, int offset);

    @Query("SELECT * FROM music.meta WHERE EXTRACT(YEAR FROM created) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM created) = EXTRACT(MONTH FROM CURRENT_DATE) ORDER BY listens DESC LIMIT :limit OFFSET :offset")
    Flux<MusicMetaEntity> findAllPerMonthWithPages(int limit, int offset);
}
