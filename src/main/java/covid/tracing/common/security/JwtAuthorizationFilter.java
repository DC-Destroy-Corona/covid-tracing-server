package covid.tracing.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 추후 request url 과 jwt.getClaims(jwtUtil.ROLE) 을 이용해서 아래와 같은 조건 추가
        // 1. 사용자가 역학조사관만이 접근할 수 있는 리소스에 접근하는 경우 제한
        // 2. 역학조사관이 사용자만이 접근할 수 있는 리소스에 접근하는 경우 제한

        final String authorizationHeader = request.getHeader(JwtUtil.HEADER_STRING);

        Long userId = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith(jwtUtil.TOKEN_PREFIX)) {
            jwt = authorizationHeader.substring(7);
            userId = jwtUtil.extractUserId(jwt);
        }

        // userId가 있으나 SecurityContextHolder 에서 세션 아이디가 없다면
        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserPrincipal userPrincipal = this.customUserDetailsService.loadUserById(userId);
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