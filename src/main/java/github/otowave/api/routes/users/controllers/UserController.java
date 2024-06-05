package github.otowave.api.routes.users.controllers;

import github.otowave.api.routes.actions.services.UserUserActions;
import github.otowave.api.routes.common.models.FaceModel;
import github.otowave.api.routes.users.services.Accessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private Accessor accessor;
    @Autowired
    UserUserActions userUserActions;

    @PostMapping("/register") //Temp unsafe realization
    private Mono<Integer> register(@RequestParam String nickname, @RequestParam String email, @RequestParam String password) {
        return accessor.register(nickname, email, password);
    }

    @GetMapping("/login") //Temp unsafe realization
    private Mono<Integer> login(@RequestParam String email, @RequestParam String password) {
        return accessor.login(email, password);
    }

    @GetMapping("/{userID}/subscribed")
    private Flux<FaceModel> subscribed(@PathVariable int userID) {
        return userUserActions.getSubscribed(userID);
    }

    @GetMapping("/{userID}/subscribers")
    private Flux<FaceModel> subscribers(@PathVariable int userID) {
        return userUserActions.getSubscribers(userID);
    }

    @PostMapping("/{userID}/subscribe")
    private Mono<Void> subscribe(@PathVariable int userID, @RequestParam int authorID) {
        return userUserActions.subscribe(userID, authorID);
    }

    @PostMapping("/{userID}/discard")
    private Mono<Void> discard(@PathVariable int userID, @RequestParam int authorID) {
        return userUserActions.discard(userID, authorID);
    }
}
