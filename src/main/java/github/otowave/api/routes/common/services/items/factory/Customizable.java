package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.routes.common.models.ProfileModel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface Customizable {
    <T extends ProfileModel> Mono<T> profile();

    Mono<Void> changeName(String newName);

    Mono<Void> changeTale(String newTale);

    Mono<Integer> delete();
}