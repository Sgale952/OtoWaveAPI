package github.otowave.api.routes.users.repositories;

import github.otowave.api.routes.users.entities.UsersMetaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsersMetaRepo extends ReactiveCrudRepository<UsersMetaEntity, Integer> {
    Mono<UsersMetaEntity> findAllByItemID(int itemID);

    @Query("UPDATE users.meta SET tale = :newTale WHERE item_id = :itemID")
    Mono<UsersMetaEntity> changeTale(int itemID, String newTale);
}
