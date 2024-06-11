package github.otowave.api.routes.common.services.items.products.songlist;

import github.otowave.api.routes.actions.services.songlists.UserPlaylistsActions;
import github.otowave.api.routes.common.services.items.factory.songlist.SonglistItem;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsFillingEntity;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsMetaEntity;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsProfileEntity;
import github.otowave.api.routes.songlists.entities.playlists.PlaylistsSecurityEntity;
import github.otowave.api.routes.songlists.models.SonglistProfileModel;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsMetaRepo;
import github.otowave.api.routes.songlists.repositories.playlists.PlaylistsProfileRepo;
import github.otowave.api.routes.songlists.services.playlists.PlaylistDeleter;
import github.otowave.api.routes.songlists.services.playlists.PlaylistsUploader;
import github.otowave.api.routes.songlists.services.playlists.faces.PlaylistsProfileMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ItemPlaylist extends SonglistItem {
    private final PlaylistsProfileRepo playlistsProfileRepo;
    private final PlaylistsMetaRepo playlistsMetaRepo;
    @Autowired
    PlaylistsProfileMaker playlistsProfileMaker;
    @Autowired
    PlaylistsUploader playlistsUploader;
    @Autowired
    PlaylistDeleter playlistDeleter;
    @Autowired
    UserPlaylistsActions userPlaylistsActions;

    @Autowired
    public ItemPlaylist(PlaylistsProfileRepo playlistsProfileRepo, PlaylistsMetaRepo playlistsMetaRepo) {
        super(DefaultImageIDs.PLAYLISTS);
        this.playlistsProfileRepo = playlistsProfileRepo;
        this.playlistsMetaRepo = playlistsMetaRepo;
    }

    @Override
    public Mono<SonglistProfileModel> profile() {
        return playlistsProfileMaker.getProfile(getItemMetaEntity());
    }

    @Override
    public Mono<Integer> changeName(String newName) {
        return playlistsProfileRepo.changeName(itemID, newName).map(PlaylistsProfileEntity::getItemID);
    }

    @Override
    public Mono<Integer> changeTale(String newTale) {
        return playlistsMetaRepo.changeTale(itemID, newTale).map(PlaylistsMetaEntity::getItemID);
    }

    @Override
    public Mono<Void> changeImage(int newImageID) {
        return getItemProfileEntity()
                .flatMap(entity -> {
                    entity.setCoverID(newImageID);
                    return playlistsProfileRepo.save(entity);
                }).then();
    }

    @Override
    public Mono<Integer> delete() {
        return playlistDeleter.delete(itemID);
    }

    @Override
    public Mono<Integer> upload(int creatorID, String title, String tale, boolean access) {
        return playlistsUploader.upload(makeProfileEntity(creatorID, title), new PlaylistsMetaEntity(tale), new PlaylistsSecurityEntity(access));
    }

    @Override
    public PlaylistsProfileEntity makeProfileEntity(int creatorID, String title) {
        return new PlaylistsProfileEntity(creatorID, title);
    }

    @Override
    public Mono<Void> addMusic(int musicID) {
        return playlistsUploader.addMusic(new PlaylistsFillingEntity(itemID, musicID));
    }

    @Override
    public Mono<Void> removeMusic(int musicID) {
        return playlistsUploader.removeMusic(itemID, musicID);
    }

    @Override
    public Mono<Void> like(int userID) {
        return userPlaylistsActions.like(userID, itemID);
    }

    @Override
    public Mono<Void> discard(int userID) {
        return userPlaylistsActions.discard(userID, itemID);
    }

    @Override
    public Mono<Integer> getCurrentImageID() {
        return getItemProfileEntity().map(PlaylistsProfileEntity::getCoverID);
    }

    @Override
    public Mono<PlaylistsProfileEntity> getItemProfileEntity() {
        return playlistsProfileRepo.findById(itemID);
    }

    @Override
    public Mono<PlaylistsMetaEntity> getItemMetaEntity() {
        return playlistsMetaRepo.findAllByItemID(itemID);
    }
}
