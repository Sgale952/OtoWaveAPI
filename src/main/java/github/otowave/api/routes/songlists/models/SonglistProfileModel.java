package github.otowave.api.routes.songlists.models;

import github.otowave.api.routes.common.models.FaceModel;
import github.otowave.api.routes.common.models.ProfileModel;
import github.otowave.api.routes.music.models.MusicFaceModel;

import java.time.LocalDateTime;
import java.util.List;

public class SonglistProfileModel extends ProfileModel {
    private final List<MusicFaceModel> filling;

    public SonglistProfileModel(FaceModel face, String tale, int likes, LocalDateTime created, List<MusicFaceModel> filling) {
        super(face, tale, likes, created);
        this.filling = filling;
    }

    public List<MusicFaceModel> getFilling() {
        return filling;
    }
}
