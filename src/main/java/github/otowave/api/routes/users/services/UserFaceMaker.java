package github.otowave.api.routes.users.services;

import github.otowave.api.routes.actions.entities.LikedAuthorsEntity;
import github.otowave.api.routes.common.models.FaceModel;
import github.otowave.api.routes.common.services.FaceMaker;
import github.otowave.api.routes.users.entities.UsersMetaEntity;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserFaceMaker extends FaceMaker<FaceModel, UsersMetaEntity, UsersProfileEntity> {
    @Autowired
    UsersProfileRepo usersProfileRepo;

    @Override
    public Flux<FaceModel> getFaceModelsFromMeta(Flux<UsersMetaEntity> metaEntities) {
        return metaEntities
                .flatMap(entity -> usersProfileRepo.findById(entity.getItemID())
                        .flatMap(this::makeFaceModel));
    }

    public Flux<FaceModel> getModelsFromLikedAuthors(Flux<LikedAuthorsEntity> likedAuthorsEntities) {
        return likedAuthorsEntities.flatMap(entity -> usersProfileRepo.findById(entity.getItemID())
                .flatMap(this::makeFaceModel));
    }

    @Override
    public Mono<FaceModel> makeFaceModel(UsersProfileEntity profileEntity) {
        int userID = profileEntity.getItemID();
        int coverID = profileEntity.getCoverID();
        String username = profileEntity.getUsername();

        return Mono.just(new FaceModel(userID, userID, coverID, username));
    }
}
