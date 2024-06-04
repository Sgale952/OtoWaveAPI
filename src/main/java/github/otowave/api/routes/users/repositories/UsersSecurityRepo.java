package github.otowave.api.routes.users.repositories;

import github.otowave.api.routes.users.entities.UsersSecurityEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsersSecurityRepo extends ReactiveCrudRepository<UsersSecurityEntity, Integer> {
    Mono<UsersSecurityEntity> findByItemID(int itemID);

    Mono<UsersSecurityEntity> findByEmailAndPassword(String email, String password);

    @Query("INSERT INTO users.security(email, password, access_role) VALUES(:email, :password, :accessRole) RETURNING *")
    Mono<UsersSecurityEntity> saveWithEmailAndPassword(String email, String password, String accessRole);
}