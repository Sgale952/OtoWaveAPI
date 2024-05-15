package github.otowave.api.security;

import static github.otowave.api.security.Authenticator.auth;
import static github.otowave.api.security.RouteAccessor.checkRouteAccess;

public class Verifier {
    //Верификация пользователя. Класс для удобного вызова методов из пакета security
    public static int verify(String token) {
        int userID = auth(token);
        checkRouteAccess(userID);
        return userID;
    }

    public static int verify(String token, int itemID) {
        int userID = auth(token);
        checkRouteAccess(userID);
        //Проверить, является ли user автором item (альбома, плейлиста, музыки и т.п.)
        return userID;
    }
}
