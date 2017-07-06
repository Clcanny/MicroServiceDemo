package demons.complicatedClient;

import demons.communicationClass.IntegerWrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by demons on 2017/6/27.
 */
@FeignClient(name = "sidecar-service")
public interface NumberFeignClient {

    @GetMapping("/two")
    public IntegerWrapper getTwo();
}
