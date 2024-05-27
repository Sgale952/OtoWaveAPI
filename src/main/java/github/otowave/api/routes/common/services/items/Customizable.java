package github.otowave.api.routes.common.services.items;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public interface Customizable {
    Mono profile();

    Mono<Void> changeName(String newName);

    Mono<Void> changeTale(String newTale);

    Mono<Void> delete();
}