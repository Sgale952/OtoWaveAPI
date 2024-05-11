package github.otowave.api.repositories.music;

import github.otowave.api.entities.music.MusicEntity;
import github.otowave.api.entities.music.MusicMetaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MusicMetaRepo extends JpaRepository<MusicMetaEntity, Integer> {
    @Query(value = "SELECT m FROM MusicMetaEntity m WHERE YEAR(uploaded) = YEAR(CURRENT_DATE) AND MONTH(uploaded) = MONTH(CURRENT_DATE)")
    Page<MusicMetaEntity> findAllPerMonth(Pageable pageRequest);
}
