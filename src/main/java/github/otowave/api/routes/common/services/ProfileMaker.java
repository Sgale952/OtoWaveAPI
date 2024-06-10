package github.otowave.api.routes.common.services;

import github.otowave.api.routes.common.entities.MetaEntity;
import github.otowave.api.routes.common.models.FaceModel;
import github.otowave.api.routes.common.models.ProfileModel;
import reactor.core.publisher.Mono;

public abstract class ProfileMaker<P extends ProfileModel, M extends MetaEntity, F extends FaceModel> {
    protected abstract Mono<P> getProfile(Mono<M> metaEntity);

    protected abstract Mono<P> makeProfile(Mono<F> faceModel, M metaEntity);
}
