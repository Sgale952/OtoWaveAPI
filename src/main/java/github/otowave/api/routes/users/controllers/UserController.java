package github.otowave.api.routes.users.controllers;

import github.otowave.api.routes.users.services.Accessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private Accessor accessor;

    @PostMapping("/register") //Temp unsafe realization
    private Mono<Integer> register(@RequestParam String nickname, @RequestParam String email, @RequestParam String password) {
        return accessor.register(nickname, email, password);
    }

    @GetMapping("/login") //Temp unsafe realization
    private Mono<Integer> login(@RequestParam String email, @RequestParam String password) {
        return accessor.login(email, password);
    }
}
