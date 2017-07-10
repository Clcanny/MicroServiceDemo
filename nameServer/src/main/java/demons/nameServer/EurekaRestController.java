package demons.nameServer;

import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by demons on 2017/7/10.
 */
@RestController
public class EurekaRestController {

    @GetMapping("/apps")
    public @ResponseBody
    List<Object> apps() {
        PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
        Applications applications = registry.getApplications();

        List<Object> apps = new ArrayList<>();
        applications.getRegisteredApplications().forEach((registeredApplication) -> {
            Map<String, Object> map = new HashMap<>();
            List<Object> list = new ArrayList<>();
            registeredApplication.getInstances().forEach((instance) -> {
                list.add(instance);
            });
            map.put("app", registeredApplication.getName());
            map.put("instance", list);
            apps.add(map);
        });
        return apps;
    }

    @GetMapping("/ips")
    public @ResponseBody
    List<String> ips() {
        PeerAwareInstanceRegistry registry = EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
        Applications applications = registry.getApplications();

        Set<String> set = new HashSet<>();
        applications.getRegisteredApplications().forEach((registeredApplication) -> {
            registeredApplication.getInstances().forEach((instance) -> {
                set.add(instance.getIPAddr());
            });
        });

        List<String> ips = new ArrayList<>();
        ips.addAll(set);
        return ips;
    }
}
