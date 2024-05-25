package github.otowave.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;


public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationWebFilter(JwtUtil jwtUtil) {
        super(new ReactiveJwtAuthenticationManager(jwtUtil));
        this.jwtUtil = jwtUtil;
        setServerAuthenticationConverter(new ServerAuthenticationConverter() {
            @Override
            public Mono<Authentication> convert(ServerWebExchange exchange) {
                String token = extractToken(exchange);
                if (token != null && jwtUtil.validateToken(token, jwtUtil.extractUserId(token))) {
                    String userId = String.valueOf(jwtUtil.extractUserId(token));
                    String role = jwtUtil.extractUserRole(token);
                    Authentication auth = new UsernamePasswordAuthenticationToken(userId, null, List.of(() -> role));
                    return Mono.just(auth);
                }
                return Mono.empty();
            }
        });
        setSecurityContextRepository(new WebSessionServerSecurityContextRepository());
    }



    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
