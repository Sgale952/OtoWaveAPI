package github.otowave.api.repositories.images;

import github.otowave.api.entities.images.ImagesEntity;
import org.springframework.data.repository.CrudRepository;

public interface ImagesRepo extends CrudRepository<ImagesEntity, Integer> {
}