package demons.complicatedClient;

import demons.communicationClass.IntegerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by demons on 2017/6/26.
 */
@RestController
public class ComplicatedController {

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
}
