package demons.account.jwt;

/**
 * Created by demons on 2017/6/27.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// Interface UserDetails provides core user information.
// Implementations are not used directly by Spring Security for security purposes.
// They simply store user information which is later encapsulated into Authentication objects.
// 不直接使用KeyUserInfo实现UserDetails的原因是：
// KeyUserInfo不宜同时承担安全服务和用户信息存储两个职责

public class JwtUser implements UserDetails {

    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(
            String email, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    @JsonIgnore
    @Override
    // 在安全服务中，username作为重要组成部分
    // 但QAServer采用邮箱作为用户唯一标识
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // 账户是否未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 账户是否激活
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
