package github.otowave.api.routes.images.repositories;

import github.otowave.api.routes.images.entities.ImagesEntity;
import org.springframework.data.repository.CrudRepository;

public interface ImagesRepo extends CrudRepository<ImagesEntity, Integer> {
}