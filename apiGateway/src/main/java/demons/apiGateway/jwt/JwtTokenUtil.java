package demons.apiGateway.jwt;

/**
 * Created by demons on 2017/6/27.
 */

import demons.apiGateway.auth.KeyUserInfo;
import demons.apiGateway.auth.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

@Service
public class JwtTokenUtil {

    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    // token = Header.Payload.Signature
    // Payload = Reserved claims | Public claims | Private claims

    // Reserved claims: These is a set of predefined claims
    // which are not mandatory but recommended, to provide a set of useful, interoperable claims.
    // Some of them are: iss (issuer), exp (expiration time), sub (subject), aud (audience), and others.

    // Public claims: These can be defined at will by those using JWTs.
    // But to avoid collisions they should be defined in the IANA JSON Web Token Registry
    // or be defined as a URI that contains a collision resistant namespace.

    // Private claims: These are the custom claims created to share information
    // between parties that agree on using them.

    public String generateToken(String username) {
        KeyUserInfo keyUserInfo = userRepository.findByEmail(username).get(0);
        if (keyUserInfo != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", keyUserInfo.getRoles().toString());
            claims.put("id", keyUserInfo.getId());

            return TOKEN_PREFIX + " " + Jwts.builder()
                    // subject是一种claims
                    // setSubjects(...)
                    .setClaims(claims)
                    .setSubject(username)
                    .setExpiration(new Date(currentTimeMillis() + EXPIRATIONTIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET)
                    .compact();
        }
        return null;
    }

    private Claims getBody(String token) {
        if (token != null) {
            try {
                return Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
            } catch (Exception exception) {
                return null;
            }
        }
        return null;
    }

    public String getUsername(String token) {
        Pair<String, Date> pair = getUsEx(token);
        if (pair != null) {
            return pair.getLeft();
        }
        return null;
    }

    public Date getExpiration(String token) {
        Pair<String, Date> pair = getUsEx(token);
        if (pair != null) {
            return pair.getRight();
        }
        return null;
    }

    public Pair<String, Date> getUsEx(String token) {
        Claims body = getBody(token);
        if (body != null) {
            return new Pair<>(body.getSubject(), body.getExpiration());
        }
        return null;
    }

    public Boolean validateToken(String token) {
        Date expiration = getExpiration(token);
        String username = getUsername(token);
        if (expiration != null && username != null) {
            return getExpiration(token).after(new Date(currentTimeMillis())) &&
                    userDetailsService.loadUserByUsername(username) != null;
        }
        return false;
    }

    public String refreshToken(String oldToken) {
        if (validateToken(oldToken)) {
            return generateToken(getUsername(oldToken));
        }
        return null;
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = getUsername(token);

            String roles = getBody(token).get("roles", String.class);
            List<GrantedAuthority> grantedAuths =
                    AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null,
                            grantedAuths) :
                    null;
        }
        return null;
    }

    public String getUsername(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        return getUsername(token);
    }

    public Boolean validate(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        return validateToken(token);
    }
}
