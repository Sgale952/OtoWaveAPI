package github.otowave.api.routes.music.repositories;

import github.otowave.api.routes.music.entities.MusicMetaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MusicMetaRepo extends ReactiveCrudRepository<MusicMetaEntity, Integer> {
    @Query(value = "SELECT m FROM MusicMetaEntity m WHERE YEAR(uploaded) = YEAR(CURRENT_DATE) AND MONTH(uploaded) = MONTH(CURRENT_DATE)")
    Page<MusicMetaEntity> findAllPerMonth(Pageable pageRequest);
}