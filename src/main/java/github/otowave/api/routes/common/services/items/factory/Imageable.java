package github.otowave.api.routes.common.services.items.factory;

import reactor.core.publisher.Mono;

public interface Imageable {
    Mono<Void> changeImage(int newImageID);

    Mono<Integer> getCurrentImageID();
}
