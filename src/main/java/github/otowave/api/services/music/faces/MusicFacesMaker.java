package github.otowave.api.services.music.faces;

import github.otowave.api.entities.music.MusicEntity;
import github.otowave.api.entities.music.MusicMetaEntity;
import github.otowave.api.entities.users.UsersProfileEntity;
import github.otowave.api.models.music.MusicFaceModel;
import github.otowave.api.repositories.music.MusicRepo;
import github.otowave.api.repositories.users.UsersProfileRepo;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class MusicFacesMaker {
    @Autowired
    private MusicRepo musicRepo;
    @Autowired
    private UsersProfileRepo usersProfileRepo;

    protected ArrayList<MusicFaceModel> getFaceModels(Iterable<MusicMetaEntity> musicEntities) {
        ArrayList<MusicFaceModel> faceModels = new ArrayList<>();

        for(MusicMetaEntity musicMetaEntity : musicEntities) {
            Iterable<MusicEntity> musicEntity = musicRepo.findAllByMusicID(musicMetaEntity.getMusicID());
            faceModels.add(makeFaceModel((MusicEntity) musicEntity));
        }
        return faceModels;
    }

    protected ArrayList<MusicFaceModel> filterByGenre(ArrayList<MusicFaceModel> musicFaces, String genre) {
        return new ArrayList<>(musicFaces.stream().filter(musicFaceModel -> Objects.equals(musicFaceModel.genre(), genre)).toList());
    }

    private MusicFaceModel makeFaceModel(MusicEntity musicEntity) {
        String title = musicEntity.getTitle();
        String author = getUsername(musicEntity.getAuthorID());
        int coverID = musicEntity.getCoverID();
        String genre = musicEntity.getGenre();
        boolean econtent = musicEntity.getEcontent();
        return new MusicFaceModel(title, author, coverID, genre, econtent);
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
