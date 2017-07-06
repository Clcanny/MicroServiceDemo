package demons.complicatedClient;

import demons.communicationClass.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
// ComplicatedClient会调用SimpleClient提供的服务
public class ComplicatedClientApplication {

    public static final String simple_client_service_url = "http://SIMPLE-CLIENT-SERVICE";

    @Autowired
    LoggingRequestInterceptor ri;

    public static void main(String[] args) {
        SpringApplication.run(ComplicatedClientApplication.class, args);
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SimpleClientService simpleClientService() {
        return new SimpleClientService(simple_client_service_url);
    }
}
