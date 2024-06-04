package github.otowave.api.routes.users.repositories;

import github.otowave.api.routes.users.entities.UsersProfileEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UsersProfileRepo extends ReactiveCrudRepository<UsersProfileEntity, Integer> {
    @Query("INSERT INTO users.profile(item_id, username) VALUES(:userID, :username) RETURNING *")
    Mono<UsersProfileEntity> saveWithUsername(int userID, String username);
}