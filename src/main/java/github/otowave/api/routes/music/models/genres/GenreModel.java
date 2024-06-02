package github.otowave.api.routes.music.models.genres;

import github.otowave.api.routes.music.models.MusicFaceModel;

import java.util.List;

public record GenreModel(String genre, List<MusicFaceModel> musicFaces) {
}
