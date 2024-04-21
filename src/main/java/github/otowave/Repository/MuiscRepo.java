package github.otowave.Repository;

import github.otowave.Entity.MusicEntity;
import org.springframework.data.repository.CrudRepository;

public interface MuiscRepo extends CrudRepository<MusicEntity, Integer> {
}
