package demons.account.jwt;

/**
 * Created by demons on 2017/6/27.
 */

import demons.account.auth.KeyUserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class JwtUserFactory {

    private JwtUserFactory() {
    }

    // 根据KeyUserInfo生成JWTUser的工厂方法
    public static JwtUser create(KeyUserInfo userInfo) {
        return new JwtUser(
                userInfo.getEmail(),
                userInfo.getPassword(),
                mapToGrantedAuthorities(userInfo.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
