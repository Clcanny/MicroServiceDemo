package demons.complicatedClient;

import demons.communicationClass.IntegerWrapper;
import demons.communicationClass.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;

/**
 * Created by demons on 2017/6/26.
 */
@Service
public class SimpleClientService {

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    public SimpleClientService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl : "http://" + serviceUrl;
    }

    public IntegerWrapper getOne() {
        return restTemplate.getForObject(serviceUrl + "/one", IntegerWrapper.class);
    }
}
