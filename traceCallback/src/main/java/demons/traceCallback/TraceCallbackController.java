package demons.traceCallback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by demons on 2017/7/7.
 */
@RestController
public class TraceCallbackController {

    @GetMapping("/{from}/{to}")
    public @ResponseBody void log(@PathVariable String from, @PathVariable String to) {
        System.out.println(from);
        System.out.println(to);
    }
}
