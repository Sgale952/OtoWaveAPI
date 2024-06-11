package github.otowave.api.routes.common.services.items.factory.item;

import github.otowave.api.routes.common.models.ProfileModel;
import reactor.core.publisher.Mono;

public interface Customizable {
    <T extends ProfileModel> Mono<T> profile();

    Mono<Integer> changeName(String newName);

    Mono<Integer> changeTale(String newTale);

    Mono<Integer> delete();
}