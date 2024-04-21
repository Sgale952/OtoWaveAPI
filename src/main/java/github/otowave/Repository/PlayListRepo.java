package github.otowave.Repository;

import github.otowave.Entity.PlaylistEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlayListRepo extends CrudRepository<PlaylistEntity,Integer> {
}
