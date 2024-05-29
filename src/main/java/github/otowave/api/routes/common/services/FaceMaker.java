package github.otowave.api.routes.common.services;

import github.otowave.api.routes.common.entities.MetaEntity;
import github.otowave.api.routes.common.entities.ProfileEntity;
import github.otowave.api.routes.common.models.FaceModel;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class FaceMaker<F extends FaceModel, M extends MetaEntity, P extends ProfileEntity> {
    @Autowired
    private UsersProfileRepo usersProfileRepo;

    public Flux<F> getFaceModelsFromProfile(Flux<P> profileEntities) {
        return profileEntities.flatMap(this::makeFaceModel);
    }

    protected abstract Flux<F> getFaceModelsFromMeta(Flux<M> metaEntities);

    protected abstract Mono<F> makeFaceModel(P profileEntity);

    protected Mono<String> getUsername(int userID) {
        return usersProfileRepo.findById(userID).map(UsersProfileEntity::getUsername);
    }
}