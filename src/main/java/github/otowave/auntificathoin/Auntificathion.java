package github.otowave.auntificathoin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;

import java.util.Date;

public class Auntificathion {
    private static final String SECRET_KEY = "h+LpvpheyFqt1Zv3JoiEmV3JsjIsCpuGDb7sjq";
    private static final long EXPIRATION_TIME = 86400000; // 24 часа (в миллисекундах)

    public static String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }
    public static boolean checkToken(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();


            String userId = claims.getSubject();
            if (userId == null || userId.isEmpty()) {
                return false;
            }


            return true;
        } catch (SignatureException e) {

            return false;
        }
    }
}
