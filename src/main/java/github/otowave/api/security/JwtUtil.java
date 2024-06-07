package github.otowave.api.security;

import github.otowave.api.routes.users.entities.UsersSecurityEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expirahion}")
    private String expirahionTime;
    public String extractId(String authToken) {
        return getClaimsFromBody(authToken)
                .getSubject();
    }

    public Claims getClaimsFromBody(String authToken) {
        String key = Base64.getEncoder().encodeToString(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken)
                .getBody();
    }

    public boolean validateToken(String authToken){
        return getClaimsFromBody(authToken)
                .getExpiration()
                .before(new Date());
    }

    public String generateToken (UsersSecurityEntity user){
        HashMap<String,Object> claims = new HashMap<>();
        claims.put("role", List.of(user.getRole()));

        long expirahiontime = Long.parseLong(expirahionTime);
        Date creationDate = new Date();
        Date expirahionDate = new Date(creationDate.getTime() + expirahiontime * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(creationDate)
                .setExpiration(expirahionDate)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }
}
