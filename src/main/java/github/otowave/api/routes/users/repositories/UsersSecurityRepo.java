package github.otowave.api.routes.users.repositories;

import github.otowave.api.routes.users.entities.UsersSecurityEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UsersSecurityRepo extends ReactiveCrudRepository<UsersSecurityEntity, Integer> {
}