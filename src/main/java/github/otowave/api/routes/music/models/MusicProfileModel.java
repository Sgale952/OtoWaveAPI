package github.otowave.api.routes.music.models;

import java.time.LocalDateTime;

public record MusicProfileModel(MusicFaceModel musicFaceModel, String tale, int likes, int listens, LocalDateTime uploaded) {
}
