package demons.complicatedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
// ComplicatedClient会调用SimpleClient提供的服务
public class ComplicatedClientApplication {

    public static final String simple_client_service_url = "http://SIMPLE-CLIENT-SERVICE";
    @Autowired
    LoggingRequestInterceptor ri;
    @Value("${spring.application.name}")
    private String name;

    public static void main(String[] args) {
        SpringApplication.run(ComplicatedClientApplication.class, args);
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        RestTemplate rt = new RestTemplate();

        //set interceptors/requestFactory
        List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
        ris.add(ri);
        rt.setInterceptors(ris);
        rt.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        return rt;
    }

    @Bean
    public SimpleClientService simpleClientService() {
        return new SimpleClientService(simple_client_service_url);
    }
}
