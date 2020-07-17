package covid.tracing.common.security;

import covid.tracing.common.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String SECRET = "1q2w3e4r5t6y7u8i9o1q2w3e4r5t6y7u";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    public static final String CLAIM_KEY_ROLE = "Role";
    public static final String CLAIM_KEY_ID = "Id";
    public static final String CLAIM_KEY_EMAIL = "Email";
    public static final String CLAIM_VALUE_ROLE_USER = "User";
    public static final String CLAIM_VALUE_ROLE_EPID = "Epid";

    public static final String HEADER_KEY_ALG = "alg";
    public static final String HEADER_KEY_TYP = "typ";
    public static final String HEADER_VALUE_ALG = "HS256";
    public static final String HEADER_VALUE_TYP = "JWT";

    public Long extractId(String token) {
        String id = extractClaim(token, Claims::getSubject);
        return Long.parseLong(id);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> headers, Map<String, Object> claims, String subject_id) {
        return Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .setSubject(subject_id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String generateToken(UserPrincipal userPrincipal, Role role) {
        logger.info("[generate token] role: " + role.toString() + " id: " + userPrincipal.getId() + " emial: " + userPrincipal.getEmail());

        Map<String, Object> headers = new HashMap<>();
        headers.put(this.HEADER_KEY_ALG, this.HEADER_VALUE_ALG);
        headers.put(this.HEADER_KEY_TYP, this.HEADER_VALUE_TYP);

        Map<String, Object> claims = new HashMap<>();
        claims.put(this.CLAIM_KEY_ROLE, role);
        claims.put(this.CLAIM_KEY_ID, userPrincipal.getId());
        claims.put(this.CLAIM_KEY_EMAIL, userPrincipal.getEmail());
        return createToken(headers, claims, String.valueOf(userPrincipal.getId()));
    }

    public Boolean validateToken(String token, UserPrincipal userPrincipal) {
        final Long userId = extractId(token);
        return (userId.equals(userPrincipal.getId()) && !isTokenExpired(token));
    }
}
