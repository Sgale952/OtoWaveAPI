package github.otowave.api.routes.common.controllers;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface Customizable {
    void profile(int itemID);

    void changeName(int itemID, String newName);

    void changeTale(int itemID, String newTale);

    void delete(int itemID);

    default <T> Mono<T> getItemEntity(int itemID, ReactiveCrudRepository<T, Integer> repo) {
        return repo.findById(itemID)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Item [" + itemID + "] Not Found")));
    }
}