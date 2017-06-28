package demons.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Map;

/**
 * Created by demons on 2017/6/27.
 */
@RestController
public class AccountController {

    @Autowired
    AuthService authService;

    public static boolean isValidEmailAddress(String email) {
        boolean valid = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            valid = false;
        }
        return valid;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> register(
            @RequestParam String email, @RequestParam String password) {
        if (!isValidEmailAddress(email)) {
            return new ResponseEntity<>(null, null, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (authService.register(email, password)) {
            return new ResponseEntity<>(null, null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, null, HttpStatus.IM_USED);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login(
            @RequestParam String email, @RequestParam String password) {
        if (!isValidEmailAddress(email)) {
            return new ResponseEntity<>(null, null, HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String token = authService.login(email, password);
        if (token != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", token);
            return new ResponseEntity<>(null, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<?> refresh(@RequestParam String token) throws AuthenticationException {
        String refreshedToken = authService.refresh(token);
        if (refreshedToken != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", refreshedToken);
            return new ResponseEntity<>(null, headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
    }
}
