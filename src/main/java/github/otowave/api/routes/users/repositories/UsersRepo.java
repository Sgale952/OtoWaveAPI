package github.otowave.api.routes.users.repositories;

import github.otowave.api.routes.users.entities.UsersEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<UsersEntity, Integer> {
}