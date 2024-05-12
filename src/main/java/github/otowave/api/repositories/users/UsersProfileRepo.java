package github.otowave.api.repositories.users;

import github.otowave.api.entities.users.UsersProfileEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersProfileRepo extends CrudRepository<UsersProfileEntity, Integer> {}