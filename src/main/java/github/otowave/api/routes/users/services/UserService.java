package github.otowave.api.routes.users.services;

import github.otowave.api.routes.users.repositories.UsersSecurityRepo;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService implements ReactiveUserDetailsService {
    private final UsersSecurityRepo userRepo;

    public UserService(UsersSecurityRepo userRepo) {
        this.userRepo = userRepo;
    }

    //TODO: change username to email
    @Override
    public Mono<UserDetails> findByUsername(String email) {
        return userRepo.findByEmail(email)
                .cast(UserDetails.class);
    }
}
