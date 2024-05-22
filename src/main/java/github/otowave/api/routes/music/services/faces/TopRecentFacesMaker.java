package github.otowave.api.routes.music.services.faces;

import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.repositories.MusicMetaRepo;
import io.micrometer.common.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class TopRecentFacesMaker extends MusicFacesMaker {
    @Autowired
    private MusicMetaRepo musicMetaRepo;

    public Flux<MusicFaceModel> getTopMusicFaceModels(int page, @Nullable String genre) {
        Flux<MusicMetaEntity> musicMetaEntity = getTopMusic(page);
        Flux<MusicFaceModel> topMusicFaceModels = getFaceModels(musicMetaEntity);
        return checkGenre(topMusicFaceModels, genre);
    }

    public Flux<MusicFaceModel> getRecentMusicFaceModels(int page, @Nullable String genre) {
        Flux<MusicMetaEntity> musicMetaEntity = getRecentMusic(page);
        Flux<MusicFaceModel> recentMusicFaceModels = getFaceModels(musicMetaEntity);
        return checkGenre(recentMusicFaceModels, genre);
    }

    private Flux<MusicMetaEntity> getTopMusic(int page) {
        return musicMetaRepo.findAllPerMonthWithPages(50, page*50);
    }

    private Flux<MusicMetaEntity> getRecentMusic(int page) {
        return musicMetaRepo.findAllWithPages(50, page*50);
    }

    private Flux<MusicFaceModel> checkGenre(Flux<MusicFaceModel> faceModels, String genre) {
        if (genre != null && !genre.isEmpty())
            return filterByGenre(faceModels, genre);
        else
            return faceModels;
    }
}
