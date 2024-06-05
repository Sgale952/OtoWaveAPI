package github.otowave.api.routes.songlists.services.playlists;

import github.otowave.api.exceptions.DuplicateMusicException;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsFillingEntity;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsMetaEntity;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsProfileEntity;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsSecurityEntity;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsFillingRepo;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsMetaRepo;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsProfileRepo;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsSecurityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class PlaylistsUploader {
    @Autowired
    PlaylistsProfileRepo playlistsProfileRepo;
    @Autowired
    PlaylistsMetaRepo playlistsMetaRepo;
    @Autowired
    PlaylistsSecurityRepo playlistsSecurityRepo;
    @Autowired
    PlaylistsFillingRepo playlistsFillingRepo;

    @Transactional
    public Mono<Integer> upload(PlaylistsProfileEntity profileEntity, PlaylistsMetaEntity metaEntity, PlaylistsSecurityEntity securityEntity) {
        return playlistsProfileRepo.save(profileEntity).flatMap(newProfileEntity -> {
            int newAlbumID = newProfileEntity.getItemID();
            metaEntity.setItemID(newAlbumID);
            securityEntity.setItemID(newAlbumID);
            return playlistsMetaRepo.save(metaEntity).then(playlistsSecurityRepo.save(securityEntity)).thenReturn(newAlbumID);
        });
    }

    @Transactional
    public Mono<Void> addMusic(PlaylistsFillingEntity fillingEntity) {
        return playlistsFillingRepo.save(fillingEntity).then()
                .onErrorResume(DuplicateKeyException.class, e ->
                        Mono.error(new DuplicateMusicException(fillingEntity.getMusicID())));
    }

    @Transactional
    public Mono<Void> removeMusic(int playlistID, int musicID) {
        return playlistsFillingRepo.deleteByItemIDAndMusicID(playlistID, musicID);
    }
}
