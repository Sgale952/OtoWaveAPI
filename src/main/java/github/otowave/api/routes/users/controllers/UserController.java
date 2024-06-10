package github.otowave.api.routes.users.controllers;

import github.otowave.api.routes.actions.services.UserUserActions;
import github.otowave.api.routes.common.models.FaceModel;
import github.otowave.api.routes.users.entities.UsersSecurityEntity;
import github.otowave.api.routes.users.services.Accessor;
import github.otowave.api.routes.users.services.UserService;
import github.otowave.api.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    private final static ResponseEntity<Object> UNAUTHORIZED = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private Accessor accessor;
    @Autowired
    private UserUserActions userUserActions;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register") //Temp unsafe realization
    private Mono<Integer> register(@RequestParam String nickname, @RequestParam String email, @RequestParam String password) {
        return accessor.register(nickname, email, password);
    }

    @GetMapping("/login") //Temp unsafe realization
    private Mono<Integer> login(@RequestParam String email, @RequestParam String password) {
        return accessor.login(email, password);
    }

    @PostMapping("/login-test")
    private Mono<ResponseEntity> loginTest(ServerWebExchange swe) {
        return swe.getFormData().flatMap(credentials ->
                userService.findByUsername(credentials.getFirst("email"))
                        .cast(UsersSecurityEntity.class)
                        .map(userDetails ->
                                Objects.equals(
                                        credentials.getFirst("password"),
                                        userDetails.getPassword()
                                )
                                        ? ResponseEntity.ok(jwtUtil.generateToken(userDetails))
                                        : UNAUTHORIZED
                        )
                        .defaultIfEmpty(UNAUTHORIZED)
        );
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
