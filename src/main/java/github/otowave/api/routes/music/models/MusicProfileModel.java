package github.otowave.api.routes.music.models;

import github.otowave.api.routes.common.models.ProfileModel;

import java.time.LocalDateTime;

public class MusicProfileModel extends ProfileModel {
    private final int listens;

    public MusicProfileModel(MusicFaceModel musicFaceModel, String tale, int likes, int listens, LocalDateTime uploaded) {
        super(musicFaceModel, tale, likes, uploaded);
        this.listens = listens;
    }

    public int getListens() {
        return listens;
    }
}
