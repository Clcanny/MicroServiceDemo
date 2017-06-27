package demons.complicatedClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
// ComplicatedClient会调用SimpleClient提供的服务
public class ComplicatedClientApplication {

    public static final String simple_client_service_url = "http://SIMPLE-CLIENT-SERVICE";

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
