package github.otowave.api.routes.users.repositories;

import github.otowave.api.routes.users.entities.UsersSecurityEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsersSecurityRepo extends ReactiveCrudRepository<UsersSecurityEntity, Integer> {
    Mono<UsersSecurityEntity> findByItemID(int itemID);

    Mono<UsersSecurityEntity> findByEmailAndPassword(String email, String password);

    @Query("INSERT INTO users.security(item_id, email, password, access_role) VALUES(:itemID, :email, :password, :accessRole) RETURNING *")
    Mono<UsersSecurityEntity> saveWithEmailAndPassword(int itemID, String email, String password, String accessRole);
}