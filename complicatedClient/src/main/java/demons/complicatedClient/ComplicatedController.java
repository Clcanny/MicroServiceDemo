package demons.complicatedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by demons on 2017/6/26.
 */
@Controller
public class ComplicatedController {

    @Autowired
    protected SimpleClientService simpleClientService;

    @GetMapping("/number")
    @ResponseBody
    public IntegerWrapper getNumber() {
        return simpleClientService.getNumber();
    }
}
