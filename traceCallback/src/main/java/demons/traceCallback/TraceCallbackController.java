package demons.traceCallback;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by demons on 2017/7/7.
 */
@RestController
public class TraceCallbackController {

    private List<Map<String, String>> table = new ArrayList<>();

    @PostMapping("/{from}/{to}")
    public @ResponseBody
    void log(@PathVariable String from, @PathVariable String to) {
        from = from.replace("%20", "/");
        System.out.println(from);
        System.out.println(to);

        Map<String, String> map = new HashMap<>();
        map.put("from", from);
        map.put("to", to);
        table.add(map);
    }

    @GetMapping("/")
    public @ResponseBody
    List<Map<String, String>> show() {
        return table;
    }
}
