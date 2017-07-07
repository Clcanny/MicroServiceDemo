package demons.communicationClass;

import com.sun.istack.internal.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;

import java.net.URI;

/**
 * Created by demons on 2017/7/7.
 */
public class RestTemplate extends org.springframework.web.client.RestTemplate {

    @Autowired
    @LoadBalanced
    org.springframework.web.client.RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String name;

    public RestTemplate() {
        super();
    }

    @LoadBalanced
    @Bean
    org.springframework.web.client.RestTemplate restTemplate() {
        return new org.springframework.web.client.RestTemplate();
    }

    @Override
    @Nullable
    protected <T> T doExecute(URI url, @Nullable HttpMethod method, @Nullable RequestCallback requestCallback,
                              @Nullable ResponseExtractor<T> responseExtractor) throws RestClientException {
        String from = name;
        String to = url.
                toString().
                replace("http://", "").
                replace("http:// www.", "").
                replace("www.", "").
                replace("/", "%20").
                toLowerCase();

        System.out.println(from);
        System.out.println(to);

        restTemplate.postForObject("http://trace-callback-service/" + from + "/" + to, null, Object.class);

        return super.doExecute(url, method, requestCallback, responseExtractor);
    }

}
