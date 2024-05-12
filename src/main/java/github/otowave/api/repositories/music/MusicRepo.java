package github.otowave.api.repositories.music;

import github.otowave.api.entities.music.MusicEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MusicRepo extends CrudRepository<MusicEntity, Integer> {}