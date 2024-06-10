package github.otowave.api.routes.actions.Models;

import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;

import java.util.List;

public record UserActionsModel(List<MusicFaceModel> createdMusic, List<MusicFaceModel> likedMusic,
                               List<SonglistFaceModel> createdPlaylists, List<SonglistFaceModel> likedPlaylists,
                               List<SonglistFaceModel> createdAlbums, List<SonglistFaceModel> likedAlbums) {
}
