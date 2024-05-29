package github.otowave.api.routes.actions.Models;

import github.otowave.api.routes.music.models.MusicFaceModel;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;
import reactor.core.publisher.Flux;

public record UserActionsModel(Flux<MusicFaceModel> createdMusic, Flux<MusicFaceModel> likedMusic,
                               Flux<SonglistFaceModel> createdPlaylists, Flux<SonglistFaceModel> likedPlaylists,
                               Flux<SonglistFaceModel> createdAlbums, Flux<SonglistFaceModel> likedAlbums) {
}
