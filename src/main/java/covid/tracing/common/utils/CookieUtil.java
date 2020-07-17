package covid.tracing.common.utils;

import javax.servlet.http.Cookie;

public class CookieUtil {

    public final static String COOKIE_NAME = "token";
    public final static int MAX_AGE_LOGIN = 60 * 60 * 3;
    public final static int MAX_AGE_LOGOUT = 0;
    public final static String PATH = "/epid/";
    public final static boolean HTTP_ONLY = true;

    public static Cookie CreateForLogin(String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setMaxAge(MAX_AGE_LOGIN);
        cookie.setPath(PATH);
        cookie.setHttpOnly(HTTP_ONLY);
        return cookie;
    }

    public static Cookie CreateForLogout() {
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setMaxAge(MAX_AGE_LOGOUT);
        cookie.setPath(PATH);
        cookie.setHttpOnly(HTTP_ONLY);
        return cookie;
    }
}
