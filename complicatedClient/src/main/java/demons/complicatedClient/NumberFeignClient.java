package demons.complicatedClient;

import demons.communicationClass.IntegerWrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by demons on 2017/6/27.
 */
@FeignClient(name = "sidecar-service")
public interface NumberFeignClient {

//    @GetMapping("/number")
    @RequestMapping(value = "/number", method = RequestMethod.GET)
    public IntegerWrapper getNumber();
}
