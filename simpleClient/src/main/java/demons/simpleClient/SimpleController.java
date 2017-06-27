package demons.simpleClient;

import demons.communicationClass.IntegerWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by demons on 2017/6/26.
 */
@RestController
public class SimpleController {

    @GetMapping("")
    @ResponseBody
    public IntegerWrapper getNum() {
	return new IntegerWrapper(2);
    }

    @GetMapping("/number")
    @ResponseBody
    public IntegerWrapper getNumber() {
        return new IntegerWrapper(1);
    }
}
