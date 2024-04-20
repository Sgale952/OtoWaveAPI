package github.otowave.Repository;

import github.otowave.Entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository <UserEntity, Integer>{
}
