package github.otowave.api.routes.music.models;

import java.util.List;

public record GenresModel(String genre, List<MusicFaceModel> musicFaces) {
}
