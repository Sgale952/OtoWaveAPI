package github.otowave.api.routes.songlists.repositories.playlists;

import github.otowave.api.routes.songlists.entities.playlists.PlaylistsMetaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PlaylistsMetaRepo extends ReactiveCrudRepository<PlaylistsMetaEntity, Integer> {
    Mono<PlaylistsMetaEntity> findAllByItemID(int itemID);

    @Query("UPDATE playlists.meta SET tale = :newTale WHERE item_id = :itemID")
    Mono<PlaylistsMetaEntity> changeTale(int itemID, String newTale);

    @Query("UPDATE playlists.meta  SET likes = Likes + 1 WHERE item_id = :playlistID")
    Mono<PlaylistsMetaEntity> increaseLikes(int playlistID);

    @Query("UPDATE playlists.meta  SET likes = Likes - 1 WHERE item_id = :musicID")
    Mono<PlaylistsMetaEntity> decriesLikes(int musicID);

    @Query("SELECT * FROM playlists.meta ORDER BY RANDOM() LIMIT 50")
    Flux<PlaylistsMetaEntity> findRandomPage();
}
