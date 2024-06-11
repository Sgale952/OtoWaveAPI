package github.otowave.api.routes.songlists.repositories.albums;

import github.otowave.api.routes.songlists.entities.albums.AlbumsProfileEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface AlbumsProfileRepo extends ReactiveCrudRepository<AlbumsProfileEntity, Integer> {
    Flux<AlbumsProfileEntity> findByCreatorID(int creatorID);

    @Query("UPDATE albums.profile SET title = :newName WHERE item_id = :itemID")
    Mono<AlbumsProfileEntity> changeName(int itemID, String newName);
}
