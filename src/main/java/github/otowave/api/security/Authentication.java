package github.otowave.api.security;

import github.otowave.api.routes.users.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class Authentication {
    @Autowired
    private UsersRepo usersRepo;

    //Проверка токена и возвращение userID
    // В случае неуспешной аутентификации / авторизации выбрасывай ResponseStatusException(HttpStatus.СТАТУСЫ_ПОСМОТРИ_В_HttpStatus, "сообщение об ошибке"))
    public static int auth(String token) {
        return tokenDecrypt(token);
    }

    private static int tokenDecrypt(String token) {
        int userID = 0;
        return userID;
    }

/*    private static final String SECRET_KEY = "h+LpvpheyFqt1Zv3JoiEmV3JsjIsCpuGDb7sjq";
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
    }*/
}

