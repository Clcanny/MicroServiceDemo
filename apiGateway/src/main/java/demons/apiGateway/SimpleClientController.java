package demons.apiGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by demons on 2017/6/28.
 */
@RestController
@RequestMapping(value = "/simple-client-service")
public class SimpleClientController {

    static String serviceUrl = "http://simple-client-service";

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/number")
    Object getNumber() {
        return restTemplate.getForObject(serviceUrl + "/number", Object.class);
    }

    @GetMapping(value = "/register")
    Object register(@RequestHeader String email, @RequestHeader String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("email", email);
        headers.add("password", password);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        return restTemplate.postForObject("http://account-service/register", entity, Object.class);
    }
}
