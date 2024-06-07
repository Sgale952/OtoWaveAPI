package github.otowave.api.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Lazy
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtUtil jwtUtil;

    public AuthenticationManager(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();

        String userid = jwtUtil.extractId(authToken);

        try{
            userid = jwtUtil.extractId(authToken);
        } catch (Exception e){
            System.out.println(e);
        }

        if(userid != null && jwtUtil.validateToken(authToken))
        {
            Claims claims = jwtUtil.getClaimsFromBody(authToken);
            List<String> role = claims.get("role",List.class);
            List<SimpleGrantedAuthority>authorites = role.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            UsernamePasswordAuthenticationToken authecahionToken = new UsernamePasswordAuthenticationToken(
                    userid,
                    null,
                    authorites
            );
            return Mono.just(authentication);
        }else{
            return Mono.empty();
        }

    }
}
