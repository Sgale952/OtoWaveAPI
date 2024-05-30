package github.otowave.api.routes.music.models;

import github.otowave.api.routes.common.models.ProfileModel;

import java.time.LocalDateTime;

public class MusicProfileModel extends ProfileModel {
    private final int listens;

    public MusicProfileModel(MusicFaceModel musicFaceModel, String tale, int likes, int listens, LocalDateTime created) {
        super(musicFaceModel, tale, likes, created);
        this.listens = listens;
    }

    public int getListens() {
        return listens;
    }
}
