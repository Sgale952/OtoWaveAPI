package github.otowave.api.routes.users.repositories;

import github.otowave.api.routes.users.entities.UsersEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UsersRepo extends ReactiveCrudRepository<UsersEntity, Integer> {
}