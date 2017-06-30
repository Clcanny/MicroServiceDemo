package demons.simpleClient;

import demons.communicationClass.IntegerWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by demons on 2017/6/26.
 */
@Controller
public class SimpleController {

    @GetMapping("/one")
    @ResponseBody
    public IntegerWrapper getOne() {
        return new IntegerWrapper(1);
    }

    @GetMapping("/addParamsInPath/{x}/{y}")
    @ResponseBody
    public IntegerWrapper getAddParamsInPath(@PathVariable Integer x, @PathVariable Integer y) {
        return new IntegerWrapper(x + y);
    }

    @GetMapping("/addParamsInHeader")
    @ResponseBody
    public IntegerWrapper getAddParamsInHeader(@RequestHeader Integer x, @RequestHeader Integer y) {
        return new IntegerWrapper(x + y);
    }

    @PostMapping("/addParamsInBody")
    @ResponseBody
    public IntegerWrapper postAddParamsInBody(@RequestParam Integer x, @RequestParam Integer y) {
        return new IntegerWrapper(x + y);
    }
}
