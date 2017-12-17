package demons.simpleClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SimpleClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleClientApplication.class, args);
    }
}
