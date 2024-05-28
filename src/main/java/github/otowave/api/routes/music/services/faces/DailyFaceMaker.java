package github.otowave.api.routes.music.services.faces;

import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.repositories.MusicMetaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
@Service
public class DailyFaceMaker extends MusicFaceMaker {
    @Autowired
    private MusicMetaRepo musicMetaRepo;

    public Flux<MusicFaceModel> getDailyMusicFaceModel() {
        Flux<MusicMetaEntity> musicMetaEntities = getDailyMusicEntity();
        return getFaceModels(musicMetaEntities);
    }

    private Flux<MusicMetaEntity> getDailyMusicEntity() {
        return musicMetaRepo.findRandomPage();
    }
}
