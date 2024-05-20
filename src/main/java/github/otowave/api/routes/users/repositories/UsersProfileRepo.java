package github.otowave.api.routes.users.repositories;

import github.otowave.api.routes.users.entities.UsersProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UsersProfileRepo extends ReactiveCrudRepository<UsersProfileEntity, Integer> {
}