package github.otowave.api.routes.users.repositories;

import github.otowave.api.routes.users.entities.UsersMetaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsersMetaRepo extends ReactiveCrudRepository<UsersMetaEntity, Integer> {
    Mono<UsersMetaEntity> findAllByItemID(int itemID);
}
