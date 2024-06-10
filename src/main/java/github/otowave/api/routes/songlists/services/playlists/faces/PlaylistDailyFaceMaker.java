package github.otowave.api.routes.songlists.services.playlists.faces;

import github.otowave.api.routes.songlists.entities.playlists.PlaylistsMetaEntity;
import github.otowave.api.routes.songlists.models.SonglistFaceModel;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsMetaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PlaylistDailyFaceMaker {
    @Autowired
    PlaylistsFaceMaker playlistsFaceMaker;
    @Autowired
    PlaylistsMetaRepo playlistsMetaRepo;

    public Flux<SonglistFaceModel> getDailyFaceModel() {
        Flux<PlaylistsMetaEntity> metaEntities = getDailyEntity();
        return playlistsFaceMaker.getFaceModelsFromMeta(metaEntities);
    }

    private Flux<PlaylistsMetaEntity> getDailyEntity() {
        return playlistsMetaRepo.findRandomPage();
    }
}
