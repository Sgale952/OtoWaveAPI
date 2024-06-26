package github.otowave.api.routes.songlists.repositories.albums;

import github.otowave.api.routes.songlists.entities.albums.AlbumsMetaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AlbumsMetaRepo extends ReactiveCrudRepository<AlbumsMetaEntity, Integer> {
    Mono<AlbumsMetaEntity> findByItemID(int itemID);

    @Query("UPDATE albums.meta SET tale = :newTale WHERE item_id = :itemID")
    Mono<AlbumsMetaEntity> changeTale(int itemID, String newTale);

    @Query("UPDATE albums.meta  SET likes = Likes + 1 WHERE item_id = :playlistID")
    Mono<AlbumsMetaEntity> increaseLikes(int playlistID);

    @Query("UPDATE albums.meta  SET likes = Likes - 1 WHERE item_id = :musicID")
    Mono<AlbumsMetaEntity> decriesLikes(int musicID);
}
