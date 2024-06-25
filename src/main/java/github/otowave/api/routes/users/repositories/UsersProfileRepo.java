package github.otowave.api.routes.users.repositories;

import github.otowave.api.routes.users.entities.UsersProfileEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsersProfileRepo extends ReactiveCrudRepository<UsersProfileEntity, Integer> {
    @Query("INSERT INTO users.profile(username) VALUES(:username) RETURNING *")
    Mono<UsersProfileEntity> saveWithUsername(String username);

    @Query("UPDATE users.profile SET username = :newName WHERE item_id = :itemID")
    Mono<UsersProfileEntity> changeName(int itemID, String newName);
}