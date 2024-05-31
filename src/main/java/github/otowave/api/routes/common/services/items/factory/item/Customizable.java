package github.otowave.api.routes.common.services.items.factory.item;

import github.otowave.api.routes.common.models.ProfileModel;
import reactor.core.publisher.Mono;

public interface Customizable {
    <T extends ProfileModel> Mono<T> profile();

    Mono<Void> changeName(String newName);

    Mono<Void> changeTale(String newTale);

    Mono<Integer> delete();
}