package demons.complicatedClient;

import demons.communicationClass.IntegerWrapper;
import demons.communicationClass.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.GET;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by demons on 2017/6/26.
 */
@RestController
public class ComplicatedController {

    private static final Logger LOG = Logger.getLogger(ComplicatedController.class.getName());

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    @Autowired
    protected SimpleClientService simpleClientService;

    @Autowired
    protected NumberFeignClient numberFeignClient;

    @GetMapping("/one")
    @ResponseBody
    public IntegerWrapper getOne() {
        return simpleClientService.getOne();
    }

    @GetMapping("/two")
    @ResponseBody
    public IntegerWrapper getTwo() {
        return numberFeignClient.getTwo();
    }

    @GetMapping("/three")
    public IntegerWrapper getThree() {
        IntegerWrapper one = restTemplate.getForObject("http://complicated-client-service/one", IntegerWrapper.class);
        IntegerWrapper three = new IntegerWrapper(one.getNumber() + 2);
        return three;
    }
}
