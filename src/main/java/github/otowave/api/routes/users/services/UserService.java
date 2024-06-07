package github.otowave.api.routes.users.services;

import github.otowave.api.routes.users.repositories.UsersSecurityRepo;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public class UserService implements ReactiveUserDetailsService {
    private final UsersSecurityRepo userRepo;

    public UserService(UsersSecurityRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return userRepo.findByUsername(username)
                .cast(UserDetails.class);
    }
}
