package github.otowave.api.services.music;

import github.otowave.api.entities.music.MusicEntity;
import github.otowave.api.entities.music.MusicMetaEntity;
import github.otowave.api.entities.users.UsersProfileEntity;
import github.otowave.api.models.music.MusicFaceModel;
import github.otowave.api.repositories.music.MusicMetaRepo;
import github.otowave.api.repositories.music.MusicRepo;
import github.otowave.api.repositories.users.UsersProfileRepo;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
public class MusicFacesMaker {
    @Autowired
    private MusicRepo musicRepo;
    @Autowired
    private MusicMetaRepo musicMetaRepo;
    @Autowired
    private UsersProfileRepo usersProfileRepo;

    public ArrayList<MusicFaceModel> getTopMusicFaceModels(int page, String genre) {
        Iterable<MusicMetaEntity> musicMetaEntities = getTopMusic(page);
        ArrayList<MusicFaceModel> faceModels = getFaceModels(musicMetaEntities);
        if(genre!=null) {
            return filterByGenre(faceModels, genre);
        }
        return faceModels;
    }

    public ArrayList<MusicFaceModel> getFreshMusicFaceModels(int page, String genre) {
        Iterable<MusicMetaEntity> musicMetaEntities = getFreshMusic(page);
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

    private Iterable<MusicMetaEntity> getFreshMusic(int page) {
        Pageable pageRequest = PageRequest.of(page, 50, Sort.by(Sort.Direction.DESC, "uploaded"));
        return musicMetaRepo.findAll(pageRequest);
    }

    private ArrayList<MusicFaceModel> getFaceModels(Iterable<MusicMetaEntity> musicEntities) {
        ArrayList<MusicFaceModel> faceModels = new ArrayList<>();

        for(MusicMetaEntity musicMetaEntity : musicEntities) {
            Iterable<MusicEntity> musicEntity = musicRepo.findAllByMusicID(musicMetaEntity.getMusicID());
            faceModels.add(makeFaceModel((MusicEntity) musicEntity));
        }
        return faceModels;
    }

    private MusicFaceModel makeFaceModel(MusicEntity musicEntity) {
        String title = musicEntity.getTitle();
        String author = getUsername(musicEntity.getAuthorID());
        int coverID = musicEntity.getCoverID();
        String genre = musicEntity.getGenre();
        boolean econtent = musicEntity.getEcontent();
        return new MusicFaceModel(title, author, coverID, genre, econtent);
    }

    private ArrayList<MusicFaceModel> filterByGenre(ArrayList<MusicFaceModel> musicFaces, String genre) {
        return new ArrayList<>(musicFaces.stream().filter(musicFaceModel -> Objects.equals(musicFaceModel.genre(), genre)).toList());
    }

    private String getUsername(int userID) {
        Optional<UsersProfileEntity> userProfile = usersProfileRepo.findNicknameByUserID(userID);
        if (userProfile.isPresent()) {
            return userProfile.get().getNickname();
        }
        else {
            throw new NoResultException();
        }
    }
}
