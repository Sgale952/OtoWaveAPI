package github.otowave.api.routes.users.repositories;

import github.otowave.api.routes.users.entities.UsersProfileEntity;
import org.springframework.data.repository.CrudRepository;

public interface UsersProfileRepo extends CrudRepository<UsersProfileEntity, Integer> {}