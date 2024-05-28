package github.otowave.api.routes.common.models;

import java.time.LocalDateTime;

public class ProfileModel {
    private final FaceModel face;
    private final String tale;
    private final int likes;
    private final LocalDateTime created;

    public ProfileModel(FaceModel face, String tale, int likes, LocalDateTime created) {
        this.face = face;
        this.tale = tale;
        this.likes = likes;
        this.created = created;
    }

    public FaceModel getFace() {
        return face;
    }

    public String getTale() {
        return tale;
    }

    public int getLikes() {
        return likes;
    }

    public LocalDateTime getCreated() {
        return created;
    }
}
