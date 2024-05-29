package github.otowave.api.routes.users.services;

import github.otowave.api.routes.actions.services.UserMusicActions;
import github.otowave.api.routes.actions.services.UserSonglistsActions;
import github.otowave.api.routes.common.models.FaceModel;
import github.otowave.api.routes.common.services.ProfileMaker;
import github.otowave.api.routes.users.entities.UsersMetaEntity;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.actions.Models.UserActionsModel;
import github.otowave.api.routes.users.models.UserProfileModel;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class UserProfileMaker extends ProfileMaker<UserProfileModel, UsersMetaEntity, FaceModel> {
    @Autowired
    UserFaceMaker userFaceMaker;
    @Autowired
    UserMusicActions userMusicActions;
    @Autowired
    UserSonglistsActions userSonglistsActions;
    @Autowired
    UsersProfileRepo usersProfileRepo;

    public UserProfileMaker() {
    }

    @Override
    public Mono<UserProfileModel> getProfile(Mono<UsersMetaEntity> metaEntity) {
        Mono<FaceModel> faceModel = userFaceMaker.getFaceModelsFromMeta(metaEntity.flux()).singleOrEmpty();
        return metaEntity.flatMap(entity -> makeProfile(faceModel, entity));
    }

    @Override
    public Mono<UserProfileModel> makeProfile(Mono<FaceModel> faceModel, UsersMetaEntity metaEntity) {
        return faceModel.flatMap(face -> {
            int userID = face.getItemID();
            String tale = metaEntity.getTale();
            int likes = metaEntity.getLikes();
            int subscribed = metaEntity.getSubscribed();
            LocalDateTime created = metaEntity.getCreated();
            return getHeaderID(metaEntity).map(headerID -> new UserProfileModel(face, tale, likes, created, headerID, subscribed, getUserActions(userID)));
        });
    }

    private Mono<Integer> getHeaderID(UsersMetaEntity metaEntity) {
        return usersProfileRepo.findById(metaEntity.getItemID())
                .map(UsersProfileEntity::getHeaderID);
    }

    private UserActionsModel getUserActions(int userID) {
        return new UserActionsModel(userMusicActions.getCreatedMusicFaces(userID), userMusicActions.getLikedMusicFaces(userID),
                userSonglistsActions.getCreatedPlaylists(userID), userSonglistsActions.getLikedPlaylists(userID),
                userSonglistsActions.getCreatedAlbums(userID), userSonglistsActions.getLikedAlbums(userID));
    }
}
