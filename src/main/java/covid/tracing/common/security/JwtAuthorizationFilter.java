package covid.tracing.common.security;

import covid.tracing.common.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        Long id = null;
        String jwt = null;
        UserPrincipal userPrincipal = null;

        logger.info("[" + uri +  "] execute filter(JwtAuthorizationFilter)... ");

        if(uri.length() == 1 || // "/" 인 경우
                uri.contains("login") ||
                uri.contains("sign-up") ||
                uri.contains("css") ||
                uri.contains("images")) {
            logger.info("early return in filter beacuse of not having token");
            filterChain.doFilter(request, response);
            return;
        }

        if(uri.contains("user")) { // header 에서 token 얻기
            final String authorizationHeader = request.getHeader(JwtUtil.HEADER_STRING);
            if(authorizationHeader != null && authorizationHeader.startsWith(jwtUtil.TOKEN_PREFIX)) {
                jwt = authorizationHeader.substring(7);
                id = jwtUtil.extractId(jwt);
            }
            userPrincipal = this.customUserDetailsService.loadUserById(id);
            logger.info("extract jwt and userId when user");
        } else if(uri.contains("epid")) { // cookie에서 token 얻기
            jwt = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(CookieUtil.COOKIE_NAME))
                    .findFirst().map(Cookie::getValue)
                    .orElse("dummy");
            id = jwtUtil.extractId(jwt);
            userPrincipal = this.customUserDetailsService.loadEpidemiologistById(id);
            logger.info("extract jwt and userId when epid");
        }

        logger.info("jwt : " + jwt);
        logger.info("id : " + id.toString());

        // 추후 request url 과 jwt.getClaims(jwtUtil.ROLE) 을 이용해서 아래와 같은 조건 추가
        // 1. 사용자가 역학조사관만이 접근할 수 있는 리소스에 접근하는 경우 제한
        // 2. 역학조사관이 사용자만이 접근할 수 있는 리소스에 접근하는 경우 제한

        // userId가 있으나 SecurityContextHolder 에서 세션 아이디가 없다면
        if(id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 토큰 유효성 검증 -> 1. 토큰의 userId와 userPrincipal의 userId가 같은지 체크  2. 토큰 유효 시간 체크
            // 만약 유효한 토큰 이라면 -> Authentication 객체를 SecurityContext에 보관 및 인증 상태를 유지하기 위해 세션에 보관
            if(jwtUtil.validateToken(jwt, userPrincipal)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userPrincipal.getEmail(), userPrincipal.getPassword(), userPrincipal.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}