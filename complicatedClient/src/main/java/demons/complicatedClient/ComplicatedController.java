package demons.complicatedClient;

import demons.communicationClass.IntegerWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/number1")
    @ResponseBody
    public IntegerWrapper getNumber1() {
        return simpleClientService.getNumber();
    }

    @GetMapping("/number2")
    @ResponseBody
    public IntegerWrapper getNumber2() {
        return numberFeignClient.getNumber();
    }
}
