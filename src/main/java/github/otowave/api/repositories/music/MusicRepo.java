package github.otowave.api.repositories.music;

import github.otowave.api.entities.music.MusicEntity;
import org.springframework.data.repository.CrudRepository;

public interface MusicRepo extends CrudRepository<MusicEntity, Integer> {
}
