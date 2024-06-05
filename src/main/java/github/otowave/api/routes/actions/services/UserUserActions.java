package github.otowave.api.routes.actions.services;

import github.otowave.api.routes.actions.repositories.LikedAuthorsRepo;
import github.otowave.api.routes.common.models.FaceModel;
import github.otowave.api.routes.users.services.UserFaceMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserUserActions {
    @Autowired
    UserFaceMaker userFaceMaker;
    @Autowired
    LikedAuthorsRepo likedAuthorsRepo;

    public Flux<FaceModel> getSubscribers(int userID) {
        return userFaceMaker.getModelsFromLikedAuthors(likedAuthorsRepo.findAllByItemID(Mono.just(userID)));
    }

    public Flux<FaceModel> getSubscribed(int userID) {
        return userFaceMaker.getModelsFromLikedAuthors(likedAuthorsRepo.findAllByUserID(Mono.just(userID)));
    }
}
