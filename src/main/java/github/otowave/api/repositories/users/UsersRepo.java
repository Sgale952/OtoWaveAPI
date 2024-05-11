package github.otowave.api.repositories.users;

import github.otowave.api.entities.users.UsersEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<UsersEntity, Integer> {
}