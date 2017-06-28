package demons.account;

/**
 * Created by demons on 2017/6/27.
 */

import demons.account.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Boolean register(String username, String password) {
        if (userRepository.findByEmail(username).size() != 0) {
            System.out.println("false");
            return false;
        }
        System.out.println(username + " " + password);
        KeyUserInfo userToAdd = new KeyUserInfo();
        // userToAdd.setId(0L);
        userToAdd.setEmail(username);
        // 服务器不保存原始密码，防止拖库等安全问题
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = password;
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.addRoles("USER");

        userRepository.save(userToAdd);
        return true;
    }

    @Override
    public String login(String username, String password) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            return null;
        }
        if (!BCrypt.checkpw(password, userDetails.getPassword())) {
            return null;
        }
        final String token = jwtTokenUtil.generateToken(username);
        System.out.println(token);
        return token;
    }

    @Override
    public String refresh(String oldToken) {
        return jwtTokenUtil.refreshToken(oldToken);
    }
}
