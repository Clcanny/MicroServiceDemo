package demons.apiGateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
}
