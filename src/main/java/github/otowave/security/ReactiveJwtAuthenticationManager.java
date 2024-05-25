package github.otowave.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import reactor.core.publisher.Mono;

public class ReactiveJwtAuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtUtil jwtUtil;
    private UserDetailsService userDetailsService = null;

    public ReactiveJwtAuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = (String) authentication.getCredentials();
        String userId = String.valueOf(jwtUtil.extractUserId(token));
        return Mono.justOrEmpty(userDetailsService.loadUserByUsername(userId))
                .filter(userDetails -> jwtUtil.validateToken(token, userDetails.getUsername()))
                .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
    }
}
