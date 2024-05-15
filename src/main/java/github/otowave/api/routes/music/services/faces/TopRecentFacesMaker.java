package github.otowave.api.routes.music.services.faces;

import github.otowave.api.routes.music.entities.MusicMetaEntity;
import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.music.repositories.MusicMetaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TopRecentFacesMaker extends MusicFacesMaker {
    @Autowired
    private MusicMetaRepo musicMetaRepo;

    public ArrayList<MusicFaceModel> getTopMusicFaceModels(int page, String genre) {
        Iterable<MusicMetaEntity> musicMetaEntities = getTopMusic(page);
        ArrayList<MusicFaceModel> faceModels = getFaceModels(musicMetaEntities);

        if(genre!=null) {
            return filterByGenre(faceModels, genre);
        }
        return faceModels;
    }

    public ArrayList<MusicFaceModel> getRecentMusicFaceModels(int page, String genre) {
        Iterable<MusicMetaEntity> musicMetaEntities = getRecentMusic(page);
        ArrayList<MusicFaceModel> faceModels = getFaceModels(musicMetaEntities);

        if(genre!=null) {
            return filterByGenre(faceModels, genre);
        }
        return faceModels;
    }

    private Iterable<MusicMetaEntity> getTopMusic(int page) {
        Pageable pageRequest = PageRequest.of(page, 50, Sort.by(Sort.Direction.DESC, "listens"));
        return musicMetaRepo.findAllPerMonth(pageRequest);
    }

    private Iterable<MusicMetaEntity> getRecentMusic(int page) {
        Pageable pageRequest = PageRequest.of(page, 50, Sort.by(Sort.Direction.DESC, "uploaded"));
        return musicMetaRepo.findAll(pageRequest);
    }
}
