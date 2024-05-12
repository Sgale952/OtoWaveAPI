package github.otowave.api.services.music.faces;

import github.otowave.api.entities.music.MusicMetaEntity;
import github.otowave.api.models.music.MusicFaceModel;
import github.otowave.api.repositories.music.MusicMetaRepo;
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
