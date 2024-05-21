package github.otowave.api.routes.common.services.items;

import reactor.core.publisher.Mono;

public interface Customizable {
    Mono<Void> profile();

    Mono<Void> changeName(String newName);

    Mono<Void> changeTale(String newTale);

    Mono<Void> delete();
}