package github.otowave.api.routes.songlists.services.albums;

import github.otowave.api.exceptions.DuplicateMusicException;
import github.otowave.api.routes.songlists.entities.albums.AlbumsFillingEntity;
import github.otowave.api.routes.songlists.entities.albums.AlbumsMetaEntity;
import github.otowave.api.routes.songlists.entities.albums.AlbumsProfileEntity;
import github.otowave.api.routes.songlists.entities.albums.AlbumsSecurityEntity;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsFillingRepo;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsMetaRepo;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsProfileRepo;
import github.otowave.api.routes.songlists.repositories.albums.AlbumsSecurityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class AlbumsUploader {
    @Autowired
    AlbumsProfileRepo albumsProfileRepo;
    @Autowired
    AlbumsMetaRepo albumsMetaRepo;
    @Autowired
    AlbumsSecurityRepo albumsSecurityRepo;
    @Autowired
    AlbumsFillingRepo albumsFillingRepo;

    public AlbumsUploader() {
    }

    public Mono<Integer> upload(AlbumsProfileEntity profileEntity, AlbumsMetaEntity metaEntity, AlbumsSecurityEntity securityEntity) {
        return albumsProfileRepo.save(profileEntity).flatMap(newProfileEntity -> {
            int newAlbumID = newProfileEntity.getItemID();
            metaEntity.setItemID(newAlbumID);
            securityEntity.setItemID(newAlbumID);
            return albumsMetaRepo.save(metaEntity).then(albumsSecurityRepo.save(securityEntity)).thenReturn(newAlbumID);
        });
    }

    public Mono<Void> addMusic(AlbumsFillingEntity fillingEntity) {
        return albumsFillingRepo.save(fillingEntity).then()
        .onErrorResume(DuplicateKeyException.class, e ->
            Mono.error(new DuplicateMusicException(fillingEntity.getMusicID())));
    }

    public Mono<Void> removeMusic(int albumID, int musicID) {
        return albumsFillingRepo.deleteByItemIDAndMusicID(albumID, musicID);
    }
}
