package github.otowave.api.routes.users.repositories;

import github.otowave.api.routes.users.entities.UsersSecurityEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsersSecurityRepo extends ReactiveCrudRepository<UsersSecurityEntity,Long> {
    Mono<UsersSecurityEntity> findByUsername(String name);
}