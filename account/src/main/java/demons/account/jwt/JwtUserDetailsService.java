package demons.account.jwt;

/**
 * Created by demons on 2017/6/27.
 */

import demons.account.auth.KeyUserInfo;
import demons.account.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // 提供一种从用户名查询用户并返回的方法
    // 返回值的类型是UserDetails而不是KeyUserInfo
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        KeyUserInfo user = userRepository.findByEmail(username).get(0);

        if (user == null) {
            return null;
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
